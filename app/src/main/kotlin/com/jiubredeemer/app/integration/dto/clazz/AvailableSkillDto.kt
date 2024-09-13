package com.jiubredeemer.app.integration.dto.clazz

import com.jiubredeemer.app.integration.dto.SkillTypeEnum

data class AvailableSkillDto(
    var type: SkillTypeEnum,
    var count: Int,
    var of: List<String?>
)
