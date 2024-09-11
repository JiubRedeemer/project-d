package com.jiubredeemer.app.integration.dto

import java.util.UUID

data class RoomDto(val roomId:UUID, val ownerId: UUID, val ruleType: RuleTypeEnum)
