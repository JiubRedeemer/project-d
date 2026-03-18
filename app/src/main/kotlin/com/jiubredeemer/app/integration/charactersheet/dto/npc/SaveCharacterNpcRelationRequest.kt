package com.jiubredeemer.app.integration.charactersheet.dto.npc

import com.jiubredeemer.app.charactersheet.npc.dto.RelationTypeEnum
import java.util.*

data class SaveCharacterNpcRelationRequest(
    var id: UUID? = null,
    var characterId: UUID? = null,
    var npcId: UUID? = null,
    var note: String? = null,
    var relationType: RelationTypeEnum? = null,
)

