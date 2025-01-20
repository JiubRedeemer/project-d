package com.jiubredeemer.app.rulebook.ability.dto

import java.util.*

data class AbilityResponse(val name: String?, val code: String?, val roomId: UUID?, val skills: List<SkillResponse>?) {
    data class SkillResponse(val id: UUID?, val name: String?, val code: String?)
}
