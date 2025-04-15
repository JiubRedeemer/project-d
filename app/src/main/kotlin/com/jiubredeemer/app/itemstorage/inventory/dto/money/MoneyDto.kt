package com.jiubredeemer.app.itemstorage.inventory.dto.money

import java.util.*


data class MoneyDto(
    val id: UUID?,
    val inventoryId: UUID?,
    val goldenCount: Long?,
    val silverCount: Long?,
    val copperCount: Long?,
)
