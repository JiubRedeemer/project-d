package com.jiubredeemer.app.itemstorage.inventory.dto.inventory

import com.jiubredeemer.app.itemstorage.inventory.dto.common.ItemStatsEnum


data class StatsDto(
    val name: ItemStatsEnum?, val options: List<String>?, val value: Any?
)
