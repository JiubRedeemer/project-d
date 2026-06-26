package com.jiubredeemer.app.combat

import com.jiubredeemer.app.charactersheet.npc.dto.NpcDto
import com.jiubredeemer.app.combat.dto.*
import com.jiubredeemer.app.integration.charactersheet.CharacterSheetClient
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.dal.entity.CombatParticipant
import com.jiubredeemer.dal.entity.CombatSession
import com.jiubredeemer.dal.repository.CombatParticipantRepository
import com.jiubredeemer.dal.repository.CombatSessionRepository
import com.jiubredeemer.dal.repository.RoomRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.web.server.ResponseStatusException
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Service
@Transactional
class CombatService(
    private val combatSessionRepository: CombatSessionRepository,
    private val combatParticipantRepository: CombatParticipantRepository,
    private val roomRepository: RoomRepository,
    private val characterSheetClient: CharacterSheetClient,
    private val combatEventPublisher: CombatEventPublisher,
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
) {

    fun createSession(roomId: UUID): CombatStateDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val existing = combatSessionRepository.findActiveByRoomId(roomId)
        if (existing.isNotEmpty()) {
            return enrichSession(existing.first(), roomId)
        }
        val room = roomRepository.findById(roomId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found")
        }
        val session = CombatSession().apply {
            this.room = room
            this.state = "PREPARING"
            this.createdAt = Timestamp.from(Instant.now())
        }
        val saved = combatSessionRepository.save(session)
        publishAfterCommit(roomId, saved.id!!)
        return enrichSession(saved, roomId)
    }

    fun getActiveSession(roomId: UUID): CombatStateDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val sessions = combatSessionRepository.findActiveByRoomId(roomId)
        return sessions.firstOrNull()?.let { enrichSession(it, roomId) }
    }

    fun addParticipant(roomId: UUID, sessionId: UUID, request: AddParticipantRequest): CombatStateDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val session = findSession(sessionId)
        val count = request.copyCount.coerceAtLeast(1).coerceAtMost(10)
        // auto-fill HP/AC from NPC template if not provided
        val npcTemplate = if (request.participantType == "NPC" && request.referenceId != null) {
            try { characterSheetClient.getNpcById(request.referenceId) } catch (_: Exception) { null }
        } else null
        val resolvedMaxHp = request.maxHp ?: npcTemplate?.let { calculateNpcHp(it) }
        val resolvedAc = request.armoryClassOverride ?: npcTemplate?.armoryClass?.toIntOrNull()
        for (i in 1..count) {
            val p = CombatParticipant().apply {
                this.session = session
                this.participantType = request.participantType
                this.referenceId = request.referenceId
                this.displayName = if (count > 1) "${request.displayName} #$i" else request.displayName
                this.copyIndex = i
                this.maxHp = resolvedMaxHp
                this.currentHp = request.currentHp ?: resolvedMaxHp
                this.armoryClass = resolvedAc
                this.isReady = false
            }
            session.participants.add(p)
        }
        combatSessionRepository.save(session)
        val updated = findSession(sessionId)
        publishAfterCommit(roomId, sessionId)
        return enrichSession(updated, roomId)
    }

    fun removeParticipant(roomId: UUID, sessionId: UUID, participantId: UUID): CombatStateDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val session = findSession(sessionId)
        session.participants.removeIf { it.id == participantId }
        combatSessionRepository.save(session)
        publishAfterCommit(roomId, sessionId)
        return enrichSession(findSession(sessionId), roomId)
    }

    fun startCombat(roomId: UUID, sessionId: UUID): CombatStateDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val session = findSession(sessionId)
        session.state = "ACTIVE"
        session.round = 1
        session.currentTurnIndex = 0
        combatSessionRepository.save(session)
        publishAfterCommit(roomId, sessionId)
        return enrichSession(findSession(sessionId), roomId)
    }

    fun setInitiative(roomId: UUID, sessionId: UUID, request: SetInitiativeRequest): CombatStateDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val session = findSession(sessionId)
        val participant = session.participants.find { it.id == request.participantId }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found")
        participant.initiative = request.initiative
        participant.isReady = true
        // once all are ready, sort by initiative descending
        val allReady = session.participants.all { it.isReady }
        if (allReady) {
            session.participants
                .sortedByDescending { it.initiative ?: -1 }
                .forEachIndexed { idx, p -> p.sortOrder = idx }
        }
        combatSessionRepository.save(session)
        publishAfterCommit(roomId, sessionId)
        return enrichSession(findSession(sessionId), roomId)
    }

    fun nextTurn(roomId: UUID, sessionId: UUID): CombatStateDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val session = findSession(sessionId)
        val sorted = session.participants.sortedWith(compareBy({ -(it.sortOrder ?: Int.MAX_VALUE) }, { -(it.initiative ?: -1) }))
        val size = sorted.size
        if (size == 0) return enrichSession(session, roomId)
        session.currentTurnIndex = (session.currentTurnIndex + 1) % size
        if (session.currentTurnIndex == 0) session.round += 1
        combatSessionRepository.save(session)
        publishAfterCommit(roomId, sessionId)
        return enrichSession(findSession(sessionId), roomId)
    }

    fun prevTurn(roomId: UUID, sessionId: UUID): CombatStateDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val session = findSession(sessionId)
        val size = session.participants.size
        if (size == 0) return enrichSession(session, roomId)
        if (session.currentTurnIndex == 0) {
            session.currentTurnIndex = size - 1
            if (session.round > 1) session.round -= 1
        } else {
            session.currentTurnIndex -= 1
        }
        combatSessionRepository.save(session)
        publishAfterCommit(roomId, sessionId)
        return enrichSession(findSession(sessionId), roomId)
    }

    fun updateParticipantHp(roomId: UUID, sessionId: UUID, request: UpdateParticipantHpRequest): CombatStateDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val session = findSession(sessionId)
        val participant = session.participants.find { it.id == request.participantId }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found")
        participant.currentHp = request.currentHp
        if (request.maxHp != null) participant.maxHp = request.maxHp
        combatSessionRepository.save(session)
        publishAfterCommit(roomId, sessionId)
        return enrichSession(findSession(sessionId), roomId)
    }

    fun updateParticipantStats(roomId: UUID, sessionId: UUID, request: UpdateParticipantStatsRequest): CombatStateDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val session = findSession(sessionId)
        val participant = session.participants.find { it.id == request.participantId }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found")
        request.currentHp?.let { participant.currentHp = it }
        request.maxHp?.let { participant.maxHp = it }
        request.armoryClass?.let { participant.armoryClass = it }
        combatSessionRepository.save(session)
        publishAfterCommit(roomId, sessionId)
        return enrichSession(findSession(sessionId), roomId)
    }

    fun endSession(roomId: UUID, sessionId: UUID) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val session = findSession(sessionId)
        combatSessionRepository.delete(session)
        TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
            override fun afterCommit() {
                combatEventPublisher.publishCombatUpdated(roomId, sessionId)
            }
        })
    }

    // ─── helpers ───────────────────────────────────────────────────────────────

    private fun calculateNpcHp(npc: NpcDto): Int? {
        val diceCount = npc.hpDiceCount ?: return npc.maxHp
        val dieSize = npc.hpDieSize ?: return npc.maxHp
        val conScore = npc.abilities?.find { it.code == "CON" }?.value?.toInt() ?: 10
        val conMod = Math.floorDiv(conScore - 10, 2)
        val flatBonus = npc.hpDiceBonus ?: 0
        val average = Math.round(diceCount * (dieSize + 1) / 2.0 + conMod * diceCount).toInt() + flatBonus
        return average.coerceAtLeast(1)
    }

    private fun publishAfterCommit(roomId: UUID, sessionId: UUID) {
        TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
            override fun afterCommit() {
                combatEventPublisher.publishCombatUpdated(roomId, sessionId)
            }
        })
    }

    private fun findSession(sessionId: UUID): CombatSession =
        combatSessionRepository.findById(sessionId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Combat session not found")
        }

    private fun enrichSession(session: CombatSession, roomId: UUID): CombatStateDto {
        val sorted = session.participants
            .sortedWith(compareBy({ it.sortOrder ?: Int.MAX_VALUE }, { -(it.initiative ?: -1) }))

        val allReady = session.participants.isNotEmpty() && session.participants.all { it.isReady }

        // fetch character data in bulk (one call per unique character reference)
        val characterRefs = sorted.filter { it.participantType == "CHARACTER" && it.referenceId != null }
            .map { it.referenceId!! }.distinct()
        val characterMap = characterRefs.associateWith { id ->
            try { characterSheetClient.findByCharacterId(id) } catch (_: Exception) { null }
        }

        // fetch npc data for NPC participants
        val npcRefs = sorted.filter { it.participantType == "NPC" && it.referenceId != null }
            .map { it.referenceId!! }.distinct()
        val npcMap = npcRefs.associateWith { id ->
            try { characterSheetClient.getNpcById(id) } catch (_: Exception) { null }
        }

        val dtos = sorted.mapIndexed { idx, p ->
            val isCurrentTurn = allReady && idx == session.currentTurnIndex && session.state == "ACTIVE"
            when (p.participantType) {
                "CHARACTER" -> {
                    val ch = p.referenceId?.let { characterMap[it] }
                    val hp = ch?.health
                    val totalAc = (ch?.armoryClass ?: 0) + (ch?.bonusArmoryClass ?: 0)
                    CombatParticipantDto(
                        id = p.id!!,
                        participantType = p.participantType,
                        referenceId = p.referenceId,
                        displayName = p.displayName ?: ch?.name ?: "Персонаж",
                        initiative = p.initiative,
                        isReady = p.isReady,
                        copyIndex = p.copyIndex,
                        sortOrder = p.sortOrder,
                        isCurrentTurn = isCurrentTurn,
                        currentHp = hp?.currentHp?.toInt() ?: p.currentHp,
                        maxHp = ((hp?.maxHp ?: 0) + (hp?.bonusValue ?: 0)).let { if (it == 0L) null else it.toInt() } ?: p.maxHp,
                        tempHp = hp?.tempHp?.toInt(),
                        armoryClass = if (totalAc == 0) null else totalAc,
                        states = ch?.states?.mapNotNull { it.stateCode } ?: emptyList(),
                        clazzName = ch?.clazzInfo?.name,
                        level = ch?.level?.level?.toInt(),
                        deathSaveSuccesses = hp?.deathSaveSuccesses,
                        deathSaveFailures = hp?.deathSaveFailures,
                        abilities = ch?.abilities,
                    )
                }
                "NPC" -> {
                    val npc: NpcDto? = p.referenceId?.let { npcMap[it] }
                    CombatParticipantDto(
                        id = p.id!!,
                        participantType = p.participantType,
                        referenceId = p.referenceId,
                        displayName = p.displayName ?: npc?.name ?: "NPC",
                        initiative = p.initiative,
                        isReady = p.isReady,
                        copyIndex = p.copyIndex,
                        sortOrder = p.sortOrder,
                        isCurrentTurn = isCurrentTurn,
                        currentHp = p.currentHp,
                        maxHp = p.maxHp ?: npc?.maxHp,
                        armoryClass = p.armoryClass ?: npc?.armoryClass?.toIntOrNull(),
                        npcType = npc?.type?.name,
                        abilities = npc?.abilities,
                    )
                }
                else -> CombatParticipantDto(
                    id = p.id!!,
                    participantType = p.participantType,
                    referenceId = p.referenceId,
                    displayName = p.displayName ?: "Участник",
                    initiative = p.initiative,
                    isReady = p.isReady,
                    copyIndex = p.copyIndex,
                    sortOrder = p.sortOrder,
                    isCurrentTurn = isCurrentTurn,
                    currentHp = p.currentHp,
                    maxHp = p.maxHp,
                )
            }
        }

        return CombatStateDto(
            sessionId = session.id!!,
            roomId = roomId,
            state = session.state,
            round = session.round,
            currentTurnIndex = session.currentTurnIndex,
            allReady = allReady,
            participants = dtos,
        )
    }
}
