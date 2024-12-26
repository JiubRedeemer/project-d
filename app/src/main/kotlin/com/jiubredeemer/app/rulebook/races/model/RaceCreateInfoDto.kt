package com.jiubredeemer.app.rulebook.races.model

import com.jiubredeemer.app.integration.dto.race.RaceStatsDto

data class RaceCreateInfoDto(val name: String, val description: String, val code: String, val stats: RaceStatsDto)
