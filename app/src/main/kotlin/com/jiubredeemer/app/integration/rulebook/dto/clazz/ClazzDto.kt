package com.jiubredeemer.app.integration.rulebook.dto.clazz

import java.util.*

data class ClazzDto(
    var id: UUID,
    val roomId: UUID,
    val name: String,
    val description: String?,
    val code: String,
    val imgUrl: String?,
    val stats: ClazzStatsDto
)
