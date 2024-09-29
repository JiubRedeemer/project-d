package com.jiubredeemer.app.classes.model

import com.jiubredeemer.app.integration.dto.clazz.ClassStatsDto

data class ClassCreateInfoDto(val name: String, val description: String, val code: String, val stats: ClassStatsDto)
