package com.jiubredeemer.app.integration.rulebook.dto.clazz

import com.jiubredeemer.app.integration.dto.SkillTypeEnum

data class AvailableSkillDto(
    val type: SkillTypeEnum,
    val count: Int,
    val of: List<String>
)
