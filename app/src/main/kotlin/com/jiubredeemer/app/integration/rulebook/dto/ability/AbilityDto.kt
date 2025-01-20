package com.jiubredeemer.app.integration.rulebook.dto.ability

import java.util.*

data class AbilityDto(val name: String?, val code: String?, val roomId: UUID?, val skills: List<SkillDto>?) {
    data class SkillDto(val id: UUID?, val name: String?, val code: String?)
}
