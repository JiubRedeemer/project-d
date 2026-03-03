package com.jiubredeemer.app.integration.rulebook.dto.background

import java.util.*

data class BackgroundStatsDto(
    val id: UUID?,
    val abilityModifiers: List<String>?,
    val traits: List<BackgroundTraitDto>?,
    val proficiencies: List<BackgroundProficiencyDto>?
)
