package com.jiubredeemer.app.integration.magic.dto

import java.util.*

data class SpellBookDto(
    val id: UUID? = null,
    val characterId: UUID? = null,
    val roomId: UUID? = null,
    val manaMax: Long? = null,
    val manaCurrent: Long? = null,
    val spells: List<SpellBookItemDto>? = null,
    val spellCells: Map<String, SpellCellDto>? = null
)
