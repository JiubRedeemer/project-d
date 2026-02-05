package com.jiubredeemer.app.integration.magic.dto

import java.util.*

data class SpellBookItemDto(
    val id: UUID? = null,
    val spellBookId: UUID? = null,
    val spellId: UUID? = null,
    val inUse: Boolean? = null,
    val spell: SpellDto? = null
)
