package com.jiubredeemer.app.races.model

import com.jiubredeemer.app.integration.dto.race.RaceStatsDto

data class RaceCreateInfoDto(val name: String, val description: String, val code: String, val stats: RaceStatsDto)
