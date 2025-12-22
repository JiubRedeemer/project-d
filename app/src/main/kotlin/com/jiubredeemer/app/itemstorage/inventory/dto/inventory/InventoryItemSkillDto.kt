package com.jiubredeemer.app.itemstorage.inventory.dto.inventory

import com.jiubredeemer.app.itemstorage.inventory.dto.item.ItemSkillsDto
import java.util.*


data class InventoryItemSkillDto(
    val id: UUID?,
    val inventoryItemId: UUID?,
    val itemSkillId: UUID?,
    val currentCharges: Int?,
    val skill: ItemSkillsDto?
)
