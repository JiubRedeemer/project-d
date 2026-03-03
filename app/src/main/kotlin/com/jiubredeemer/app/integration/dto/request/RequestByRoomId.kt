package com.jiubredeemer.app.integration.dto.request

import com.jiubredeemer.app.integration.dto.RuleTypeEnum
import java.util.*

data class RequestByRoomId(val roomId: UUID, val forceRuleType: RuleTypeEnum?)
