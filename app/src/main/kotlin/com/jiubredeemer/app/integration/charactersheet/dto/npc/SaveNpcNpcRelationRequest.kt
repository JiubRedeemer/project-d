package com.jiubredeemer.app.integration.charactersheet.dto.npc

import com.jiubredeemer.app.charactersheet.npc.dto.RelationTypeEnum
import java.util.UUID

data class SaveNpcNpcRelationRequest(
    var id: UUID? = null,
    var fromNpcId: UUID? = null,
    var toNpcId: UUID? = null,
    var note: String? = null,
    var relationType: RelationTypeEnum? = null,
)
