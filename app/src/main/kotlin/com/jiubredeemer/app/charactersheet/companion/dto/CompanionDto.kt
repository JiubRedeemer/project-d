package com.jiubredeemer.app.charactersheet.companion.dto

import com.jiubredeemer.app.charactersheet.character.dto.CharacterDto
import java.time.LocalDateTime
import java.util.*

data class CompanionDto(
    val id: UUID? = null,
    val characterId: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val companionType: String? = null,
    val maxHp: Int? = null,
    val currentHp: Int? = null,
    val hpDiceCount: Int? = null,
    val hpDieSize: Int? = null,
    val hpDiceBonus: Int? = null,
    val armoryClass: String? = null,
    val speed: String? = null,
    val level: Int? = null,
    val proficiencyBonus: Int? = null,
    val challengeRating: String? = null,
    val skills: List<CompanionSkillDto>? = null,
    val actions: List<CompanionActionDto>? = null,
    val features: List<CompanionFeatureDto>? = null,
    val abilities: List<CharacterDto.AbilityShort>? = null,
    val imgUrl: String? = null,
    val sourceNpcId: UUID? = null,
    val createdAt: LocalDateTime? = null,
) {
    data class CompanionSkillDto(val name: String? = null, val bonus: Int? = null)
    data class CompanionActionDto(val name: String? = null, val description: String? = null)
    data class CompanionFeatureDto(val name: String? = null, val description: String? = null)
}
