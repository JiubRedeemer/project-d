package com.jiubredeemer.app.room.model.response

import com.jiubredeemer.app.integration.dto.RuleTypeEnum
import java.sql.Timestamp
import java.util.UUID

data class RoomMasterResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val filePath: String?,
    val lastActivityDate: Timestamp?,
    val ruleType: RuleTypeEnum?,
    val baseRuleType: RuleTypeEnum?
)
