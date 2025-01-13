package com.jiubredeemer.app.rulebook.clazz.model

import com.jiubredeemer.app.integration.rulebook.dto.clazz.ClazzStatsDto

data class ClassCreateInfoDto(val name: String, val description: String, val code: String, val stats: ClazzStatsDto)
