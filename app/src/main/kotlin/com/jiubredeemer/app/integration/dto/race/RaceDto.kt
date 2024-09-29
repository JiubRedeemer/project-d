package com.jiubredeemer.app.integration.dto.race

import java.util.*

data class RaceDto(
    val id: UUID,
    val roomId: UUID,
    val name: String,
    val code: String,
    val description: String?,
    val stats: RaceStatsDto
)
