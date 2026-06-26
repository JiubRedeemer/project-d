package com.jiubredeemer.app.combat

import com.jiubredeemer.app.combat.dto.*
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/rooms/{roomId}/combat")
@Tag(name = "Боевой трекер", description = "API для управления боевым трекером")
class CombatApiController(
    private val combatService: CombatService,
) {

    @PostMapping("")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createSession(@PathVariable roomId: UUID): CombatStateDto =
        combatService.createSession(roomId)

    @GetMapping("/active")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getActiveSession(@PathVariable roomId: UUID): ResponseEntity<CombatStateDto> {
        val session = combatService.getActiveSession(roomId)
        return if (session != null) ResponseEntity.ok(session) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{sessionId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun endSession(@PathVariable roomId: UUID, @PathVariable sessionId: UUID): ResponseEntity<Void> {
        combatService.endSession(roomId, sessionId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{sessionId}/participants")
    @HasRoleOrThrow("ADMIN", "USER")
    fun addParticipant(
        @PathVariable roomId: UUID,
        @PathVariable sessionId: UUID,
        @RequestBody request: AddParticipantRequest,
    ): CombatStateDto = combatService.addParticipant(roomId, sessionId, request)

    @DeleteMapping("/{sessionId}/participants/{participantId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun removeParticipant(
        @PathVariable roomId: UUID,
        @PathVariable sessionId: UUID,
        @PathVariable participantId: UUID,
    ): CombatStateDto = combatService.removeParticipant(roomId, sessionId, participantId)

    @PatchMapping("/{sessionId}/start")
    @HasRoleOrThrow("ADMIN", "USER")
    fun startCombat(@PathVariable roomId: UUID, @PathVariable sessionId: UUID): CombatStateDto =
        combatService.startCombat(roomId, sessionId)

    @PatchMapping("/{sessionId}/initiative")
    @HasRoleOrThrow("ADMIN", "USER")
    fun setInitiative(
        @PathVariable roomId: UUID,
        @PathVariable sessionId: UUID,
        @RequestBody request: SetInitiativeRequest,
    ): CombatStateDto = combatService.setInitiative(roomId, sessionId, request)

    @PatchMapping("/{sessionId}/next-turn")
    @HasRoleOrThrow("ADMIN", "USER")
    fun nextTurn(@PathVariable roomId: UUID, @PathVariable sessionId: UUID): CombatStateDto =
        combatService.nextTurn(roomId, sessionId)

    @PatchMapping("/{sessionId}/prev-turn")
    @HasRoleOrThrow("ADMIN", "USER")
    fun prevTurn(@PathVariable roomId: UUID, @PathVariable sessionId: UUID): CombatStateDto =
        combatService.prevTurn(roomId, sessionId)

    @PatchMapping("/{sessionId}/participants/hp")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateParticipantHp(
        @PathVariable roomId: UUID,
        @PathVariable sessionId: UUID,
        @RequestBody request: UpdateParticipantHpRequest,
    ): CombatStateDto = combatService.updateParticipantHp(roomId, sessionId, request)

    @PatchMapping("/{sessionId}/participants/stats")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateParticipantStats(
        @PathVariable roomId: UUID,
        @PathVariable sessionId: UUID,
        @RequestBody request: UpdateParticipantStatsRequest,
    ): CombatStateDto = combatService.updateParticipantStats(roomId, sessionId, request)
}
