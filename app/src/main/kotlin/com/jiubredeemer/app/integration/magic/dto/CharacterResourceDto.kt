package com.jiubredeemer.app.integration.magic.dto

import java.util.*

data class CharacterResourceDto(
    val id: UUID? = null,
    val spellBookId: UUID? = null,
    val name: String? = null,
    val icon: String? = null,
    val maxCount: Long? = null,
    val currentCount: Long? = null,
    val refillRestType: ChargesRefillEnum? = null,
)
