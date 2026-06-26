package com.jiubredeemer.app.integration.charactersheet.dto.companion

import com.jiubredeemer.app.charactersheet.companion.dto.CompanionDto
import java.util.*

data class SaveCompanionRequest(
    var id: UUID? = null,
    var characterId: UUID? = null,
    var name: String? = null,
    var description: String? = null,
    var companionType: String? = null,
    var maxHp: Int? = null,
    var currentHp: Int? = null,
    var hpDiceCount: Int? = null,
    var hpDieSize: Int? = null,
    var hpDiceBonus: Int? = null,
    var armoryClass: String? = null,
    var speed: String? = null,
    var level: Int? = null,
    var proficiencyBonus: Int? = null,
    var challengeRating: String? = null,
    var skills: List<CompanionDto.CompanionSkillDto>? = null,
    var actions: List<CompanionDto.CompanionActionDto>? = null,
    var features: List<CompanionDto.CompanionFeatureDto>? = null,
    var strScore: Int? = null,
    var dexScore: Int? = null,
    var conScore: Int? = null,
    var intScore: Int? = null,
    var wisScore: Int? = null,
    var chaScore: Int? = null,
    var imgUrl: String? = null,
    var sourceNpcId: UUID? = null,
)
