package com.jiubredeemer.app.itemstorage.inventory.dto.item

import java.time.LocalDateTime
import java.util.*

data class SearchItemParams(
    val searchQuery: String,
    val limit: Int,
    val lastSeenCreatedAt: LocalDateTime? = null,
    val lastSeenId: UUID? = null,
    val ruleType: String? = null,
    val type: String? = null,
    val subtype: String? = null,
    val rarity: String? = null,
    val tags: List<String>? = null,
    val customization: Boolean? = null,
    val hasSkills: Boolean? = null
)
