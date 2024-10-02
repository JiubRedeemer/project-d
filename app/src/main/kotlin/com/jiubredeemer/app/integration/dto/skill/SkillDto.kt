package com.jiubredeemer.app.integration.dto.skill

import com.jiubredeemer.app.integration.dto.ability.AbilityDto
import java.util.*

class SkillDto(val id: UUID, val name: String, val code: String, val dependOnAbility: UUID, val ability: AbilityDto)
