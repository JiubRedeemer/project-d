package com.jiubredeemer.app.integration.dto.room

import com.jiubredeemer.app.integration.dto.RuleTypeEnum
import java.util.UUID

data class RoomCreateRequestDto(val roomId:UUID, val ownerId: UUID, val ruleType: RuleTypeEnum)
