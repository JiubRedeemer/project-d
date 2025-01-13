package com.jiubredeemer.app.integration.rulebook.dto.race

import java.util.*

data class RaceTraitsDto(
    val id: UUID,
    val raceStatsId: UUID,
    val name: String,
    val code: String,
    val description: String
)
