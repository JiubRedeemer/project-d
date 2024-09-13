package com.jiubredeemer.app.integration.dto.clazz

import java.util.*

data class ClassStatsDto(
    val id: UUID,
    val hpDice: String,
    val savingThrowsAbilities: List<AbilityShortDto>,
    val availableSkills: List<AvailableSkillDto>
)
