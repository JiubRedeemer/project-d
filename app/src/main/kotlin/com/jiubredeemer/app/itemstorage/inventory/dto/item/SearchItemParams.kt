package com.jiubredeemer.app.itemstorage.inventory.dto.item

import java.time.LocalDateTime
import java.util.*

data class SearchItemParams(
    val searchQuery: String,
    val limit: Int,
    val lastSeenCreatedAt: LocalDateTime? = null,
    val lastSeenId: UUID? = null
)
