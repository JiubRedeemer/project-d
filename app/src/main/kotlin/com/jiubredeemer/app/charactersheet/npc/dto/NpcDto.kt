package com.jiubredeemer.app.charactersheet.npc.dto

import com.jiubredeemer.app.integration.rulebook.dto.race.RaceTraitDto
import java.time.LocalDateTime
import java.util.*

data class NpcDto(
    val id: UUID? = null,
    val relationId: UUID?,
    val roomId: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val visible: Boolean? = null,
    val unique: Boolean? = null,
    val type: NpcTypeEnum? = null,
    val clazzCode: String? = null,
    val clazzInfo: ClassInfoDto? = null,
    val raceCode: String? = null,
    val raceInfo: RaceInfoDto? = null,
    val armoryClass: String? = null,
    val speed: String? = null,
    val initiative: Int? = null,
    val imgUrl: String? = null,
    val createdBy: UUID? = null,
    val createdAt: LocalDateTime? = null,
    val tags: List<String>? = null,
) {
    data class ClassInfoDto(
        val code: String? = null,
        val name: String? = null,
    )

    data class RaceInfoDto(
        val code: String? = null,
        val name: String? = null,
        val speciesCode: String? = null,
        val imgUrl: String? = null,
        val traits: List<RaceTraitDto>? = null,
    )
}

