package com.jiubredeemer.app.charactersheet.npc.dto

import java.util.UUID

data class NpcNpcRelationDto(
    val id: UUID? = null,
    val fromNpcId: UUID? = null,
    val toNpcId: UUID? = null,
    val note: String? = null,
    val relationType: RelationTypeEnum? = null,
)
