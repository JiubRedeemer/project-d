package com.jiubredeemer.app.integration.rulebook.dto.race

import java.util.*

data class RaceStatsDto(
    val id: UUID?,
    val maxAge: Int?,
    val maxHeight: Int?,
    val maxWeight: Int?,
    val baseSpeed: Int,
    val abilityModifiers: List<AbilityModifierDto>,
    val traits: List<RaceTraitDto>?,
    val proficiencies: List<RaceProficiencyDto>?
)
