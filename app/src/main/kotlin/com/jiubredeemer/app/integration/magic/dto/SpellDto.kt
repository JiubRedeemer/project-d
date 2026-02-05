package com.jiubredeemer.app.integration.magic.dto

import java.time.Instant
import java.util.*

data class SpellDto(
    val id: UUID? = null,
    val name: Map<String, String>? = null,
    val level: String? = null,
    val spellClass: String? = null,
    val school: String? = null,
    val ritual: Boolean? = null,
    val customization: Boolean? = null,
    val damageType: String? = null,
    val healType: String? = null,
    val savingThrow: String? = null,
    val useTime: String? = null,
    val distance: String? = null,
    val duration: String? = null,
    val components: String? = null,
    val description: String? = null,
    val createdAt: Instant? = null,
    /** When creating a spell, set to the character ID; stored as createdBy */
    val characterId: UUID? = null,
    /** Who created the spell (e.g. TTG for imported, or character ID for user-created) */
    val createdBy: String? = null,
    val imgUrl: String? = null
)
