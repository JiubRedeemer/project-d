package com.jiubredeemer.app.charactersheet.character.dto

import java.util.*

data class CreateCharacterRequest(
    val roomId: UUID,
    val name: String,
    val clazzCode: String,
    val raceCode: String,
    val abilities: List<AbilityShort>,
    val skills: List<SkillShort>,
    val age: Long,
    val height: Long,
    val weight: Long,
    val attachments: String,
    val ideals: String,
    val personality: String,
    val relationships: String,
    val weaknesses: String
) {
    data class AbilityShort(
        val code: String,
        val value: Long
    )
    data class SkillShort(
        val code: String,
        val type: String
    )
}
