package com.jiubredeemer.app.charactersheet.character.dto

import java.util.*

data class CharacterDto(
    val id: UUID?,
    val roomId: UUID?,
    val userId: UUID?,
    val name: String?,
    val clazzCode: String?,
    val clazzInfo: ClassInfoDto?,
    val raceCode: String?,
    val raceInfo: RaceInfoDto?,
    val proficiencyBonus: Int?,
    val armoryClass: Int?,
    val bonusArmoryClass: Int?,
    val speed: Int?,
    val bonusSpeed: Int?,
    val inspiration: Int?,
    val initiative: Int?,
    val bonusInitiative: Int?,
    val abilities: List<AbilityShort>?,
    val skills: List<SkillShort>?,
    val characterBio: CharacterBio?,
    val health: HealhDto?,
    val level: LevelDto?
) {
    data class AbilityShort(
        val code: String?,
        val value: Long?,
        val bonusValue: Long?
    )

    data class SkillShort(
        val code: String?,
    )

    data class CharacterBio(
        var characterId: UUID?,
        val age: Long?,
        val height: Long?,
        val weight: Long?,
        val attachments: String?,
        val history: String?,
        val ideals: String?,
        val personality: String?,
        val relationships: String?,
        val weaknesses: String?
    )

    data class HealhDto(
        val characterId: UUID?,
        val currentHp: Long?,
        val maxHp: Long?,
        val tempHp: Long?,
        val bonusValue: Long?
    )

    data class LevelDto(
        var characterId: UUID?,
        val level: Long?,
        val xp: Long?,
        val nextLevelXp: Long?
    )

    data class ClassInfoDto(
        var code: String?,
        val name: String?,
    )

    data class RaceInfoDto(
        var code: String?,
        val name: String?,
    )
}
