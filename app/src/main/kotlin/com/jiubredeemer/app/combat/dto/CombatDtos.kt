package com.jiubredeemer.app.combat.dto

import com.jiubredeemer.app.charactersheet.character.dto.CharacterDto
import java.util.*

data class AddParticipantRequest(
    val participantType: String,
    val referenceId: UUID?,
    val displayName: String?,
    val copyCount: Int = 1,
    val maxHp: Int? = null,
    val currentHp: Int? = null,
    val armoryClassOverride: Int? = null,
)

data class SetInitiativeRequest(
    val participantId: UUID,
    val initiative: Int,
)

data class UpdateParticipantHpRequest(
    val participantId: UUID,
    val currentHp: Int,
    val maxHp: Int? = null,
)

data class UpdateParticipantStatsRequest(
    val participantId: UUID,
    val currentHp: Int? = null,
    val maxHp: Int? = null,
    val armoryClass: Int? = null,
)

data class CombatParticipantDto(
    val id: UUID,
    val participantType: String,
    val referenceId: UUID?,
    val displayName: String,
    val initiative: Int?,
    val isReady: Boolean,
    val copyIndex: Int,
    val sortOrder: Int?,
    val isCurrentTurn: Boolean,
    val currentHp: Int?,
    val maxHp: Int?,
    // enriched from CharacterSheet for CHARACTER type
    val tempHp: Int? = null,
    val armoryClass: Int? = null,
    val states: List<String> = emptyList(),
    val clazzName: String? = null,
    val level: Int? = null,
    val deathSaveSuccesses: Int? = null,
    val deathSaveFailures: Int? = null,
    // for NPC type
    val npcType: String? = null,
    val abilities: List<CharacterDto.AbilityShort>? = null,
)

data class CombatStateDto(
    val sessionId: UUID,
    val roomId: UUID,
    val state: String,
    val round: Int,
    val currentTurnIndex: Int,
    val allReady: Boolean,
    val participants: List<CombatParticipantDto>,
)
