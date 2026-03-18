package com.jiubredeemer.app.integration.charactersheet.dto.npc

import com.jiubredeemer.app.charactersheet.npc.dto.NpcTypeEnum
import java.util.*

data class SaveNpcRequest(
    var id: UUID? = null,
    var roomId: UUID? = null,
    var name: String? = null,
    var description: String? = null,
    var type: NpcTypeEnum? = null,
    var clazzCode: String? = null,
    var raceCode: String? = null,
    var armoryClass: String? = null,
    var speed: String? = null,
    var initiative: Int? = null,
    var imgUrl: String? = null,
    var createdBy: UUID? = null,
)

