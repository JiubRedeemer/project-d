package com.jiubredeemer.app.integration.rulebook.dto.race

import com.jiubredeemer.app.integration.dto.RaceProficiencyTypeEnum
import java.util.*

data class RaceProficiencyDto(
    val id: UUID,
    val raceStatsId: UUID,
    val type: RaceProficiencyTypeEnum,
    val code: String
)
