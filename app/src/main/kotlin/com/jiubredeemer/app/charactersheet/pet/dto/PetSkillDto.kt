package com.jiubredeemer.app.charactersheet.pet.dto

import java.util.*

data class PetSkillDto(
    val id: UUID? = null,
    val petId: UUID? = null,
    val name: String? = null,
    val description: String? = null,
)
