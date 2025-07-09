package com.jiubredeemer.app.itemstorage.inventory.dto.inventory


data class EquippedItemsStatsResponse(
    val armoryClassBonus: List<StatsDto>?,
    val speedBonus: List<StatsDto?>,
    val hpBonus: List<StatsDto>?
)
