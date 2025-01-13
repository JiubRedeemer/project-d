package com.jiubredeemer.app.integration.rulebook.dto.clazz

import java.util.*

data class ClazzStatsDto(
    val id: UUID,
    val hpDice: String,
    val savingThrowsAbilities: List<AbilityShortDto>,
    val availableSkills: List<AvailableSkillDto>
)
