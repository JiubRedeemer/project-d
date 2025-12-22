package com.jiubredeemer.app.itemstorage.inventory.dto.inventory

import com.jiubredeemer.app.itemstorage.inventory.dto.item.ItemDto
import java.util.*

data class InventoryItemDto (
    val id: UUID?,
    val inventoryId: UUID?,
    val itemId: UUID?,
    val item: ItemDto?,
    val count: Long?,
    val inUse: Boolean?,
    val skills: List<InventoryItemSkillDto>?

)
