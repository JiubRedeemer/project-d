package com.jiubredeemer.app.integration.dto.race

import java.util.*

data class RaceStatsDto(
    private var id: UUID,
    val maxAge: Int,
    val maxHeight: Int,
    val maxWeight: Int,
    val baseSpeed: Int,
    val abilityModifiers: List<AbilityModifierDto>,
    val traits: List<RaceTraitsDto>,
    val proficiencies: List<RaceProficienciesDto>
)
