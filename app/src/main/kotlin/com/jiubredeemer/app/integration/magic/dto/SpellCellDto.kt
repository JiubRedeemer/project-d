package com.jiubredeemer.app.integration.magic.dto

import java.util.*

data class SpellCellDto(
    val id: UUID? = null,
    val spellBookId: UUID? = null,
    val level: Long? = null,
    val maxCount: Long? = null,
    val currentCount: Long? = null,
    val refillRestType: ChargesRefillEnum? = null
)
