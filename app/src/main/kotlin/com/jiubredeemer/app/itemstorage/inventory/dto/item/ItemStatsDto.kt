package com.jiubredeemer.app.itemstorage.inventory.dto.item

import com.fasterxml.jackson.annotation.JsonInclude
import com.jiubredeemer.app.itemstorage.inventory.dto.common.DamageObject
import com.jiubredeemer.app.itemstorage.inventory.dto.common.PriceObject

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ItemStatsDto(
    val defaultPrice: List<PriceObject>?,
    val weight: Long?,
    val damage: DamageObject?,
    val armorClass: String?,
    val armorClassMaxDexterityBonus: String?,
    val requirement: String?,
    val tags: List<String>?,
)
