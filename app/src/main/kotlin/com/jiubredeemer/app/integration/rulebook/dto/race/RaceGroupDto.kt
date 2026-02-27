package com.jiubredeemer.app.integration.rulebook.dto.race

data class RaceGroupDto(
    val species: RaceDto?,
    val subspecies: List<RaceDto> = emptyList()
)
