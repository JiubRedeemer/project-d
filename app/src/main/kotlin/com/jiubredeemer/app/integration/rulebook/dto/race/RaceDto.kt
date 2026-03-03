package com.jiubredeemer.app.integration.rulebook.dto.race

import java.util.*

data class RaceDto(
    val id: UUID?,
    val roomId: UUID,
    val name: String,
    val description: String?,
    val code: String?,
    val speciesCode: String?,
    val imgUrl: String?,
    val stats: RaceStatsDto
)
