package com.jiubredeemer.app.integration.dto.clazz

import java.util.*

data class ClassDto(
    var id: UUID,
    val roomId: UUID,
    val name: String,
    val description: String?,
    val code: String,
    val stats: ClassStatsDto
)
