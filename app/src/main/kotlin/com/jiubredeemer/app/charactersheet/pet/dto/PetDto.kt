package com.jiubredeemer.app.charactersheet.pet.dto

import java.util.*

data class PetDto(
    val id: UUID? = null,
    val characterId: UUID? = null,
    val name: String? = null,
    val age: Long? = null,
    val description: String? = null,
    val avatar: String? = null,
    val maxHp: Long? = null,
    val currentHp: Long? = null,
    val armorClass: Int? = null,
    val speed: Int? = null,
    val size: String? = null,
    val creatureType: String? = null,
    val proficiencyBonus: Int? = null,
    val senses: String? = null,
    val languages: String? = null,
    val isSummoned: Boolean? = null,
    val isActive: Boolean? = null,
    val abilities: List<PetAbilityDto>? = null,
    val skills: List<PetSkillDto>? = null,
)
