package com.jiubredeemer.app.integration.dto.race

import com.jiubredeemer.app.integration.dto.RaceProficienciesTypeEnum
import java.util.*

data class RaceProficienciesDto(
    val id: UUID,
    val raceStatsId: UUID,
    val type: RaceProficienciesTypeEnum,
    val code: String
)
