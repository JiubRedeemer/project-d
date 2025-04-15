package com.jiubredeemer.app.itemstorage.inventory.dto.inventory

import java.util.*

data class InventoryDto(
    val id: UUID?,
    val roomId: UUID?,
    val characterId: UUID?,
    val totalWeight: Long?,
    val items: List<InventoryItemDto>?
)

