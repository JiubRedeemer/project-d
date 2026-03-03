package com.jiubredeemer.app.integration.rulebook.dto.background

import java.util.*

data class BackgroundProficiencyDto(
    val id: UUID,
    val backgroundStatsId: UUID,
    val type: BackgroundProficiencyTypeEnum,
    val code: String
)
