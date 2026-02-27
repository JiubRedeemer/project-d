package com.jiubredeemer.app.integration.rulebook.dto.clazz

data class ClazzGroupDto(
    val clazz: ClazzDto?,
    val subClazzes: List<ClazzDto> = emptyList()
)
