package com.jiubredeemer.app.integration.charactersheet.dto.npc

import com.jiubredeemer.app.charactersheet.npc.dto.NpcTypeEnum
import java.util.*

data class NpcSkillDto(val name: String? = null, val bonus: Int? = null)
data class NpcActionDto(val name: String? = null, val description: String? = null)
data class NpcFeatureDto(val name: String? = null, val description: String? = null)

data class SaveNpcRequest(
    var id: UUID? = null,
    var roomId: UUID? = null,
    var name: String? = null,
    var description: String? = null,
    var visible: Boolean? = null,
    var unique: Boolean? = null,
    var type: NpcTypeEnum? = null,
    var clazzCode: String? = null,
    var raceCode: String? = null,
    var armoryClass: String? = null,
    var speed: String? = null,
    var initiative: Int? = null,
    var maxHp: Int? = null,
    var hpDiceCount: Int? = null,
    var hpDieSize: Int? = null,
    var hpDiceBonus: Int? = null,
    var level: Int? = null,
    var proficiencyBonus: Int? = null,
    var challengeRating: String? = null,
    var skills: List<NpcSkillDto>? = null,
    var actions: List<NpcActionDto>? = null,
    var features: List<NpcFeatureDto>? = null,
    var strScore: Int? = null,
    var dexScore: Int? = null,
    var conScore: Int? = null,
    var intScore: Int? = null,
    var wisScore: Int? = null,
    var chaScore: Int? = null,
    var imgUrl: String? = null,
    var createdBy: UUID? = null,
    var tags: List<String>? = null,
)

