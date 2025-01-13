package com.jiubredeemer.app.rulebook.race.model

import com.jiubredeemer.app.integration.rulebook.dto.race.RaceStatsDto

data class RaceCreateInfoDto(val name: String, val description: String, val code: String, val stats: RaceStatsDto)
