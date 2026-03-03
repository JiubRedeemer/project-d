package com.jiubredeemer.app.integration.rulebook.dto.background

import java.util.*

data class BackgroundTraitDto(
    val id: UUID,
    val backgroundStatsId: UUID,
    val name: String,
    val code: String,
    val description: String?
)
