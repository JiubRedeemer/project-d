package com.jiubredeemer.app.charactersheet.npc.dto

import java.util.*

data class CharacterNpcRelationDto(
    val id: UUID? = null,
    val characterId: UUID? = null,
    val npcId: UUID? = null,
    val note: String? = null,
    val relationType: RelationTypeEnum? = null,
)

