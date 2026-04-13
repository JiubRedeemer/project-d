package com.jiubredeemer.app.charactersheet.pet.dto

import java.util.*

data class PetAbilityDto(
    val id: UUID? = null,
    val petId: UUID? = null,
    val abilityCode: String? = null,
    val bonusValue: Long? = null,
)
