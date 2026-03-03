package com.jiubredeemer.app.integration.rulebook.dto.background

import java.util.*

data class BackgroundDto(
    val id: UUID,
    val roomId: UUID,
    val name: String,
    val description: String?,
    val code: String,
    val imgUrl: String?,
    val stats: BackgroundStatsDto?
)
