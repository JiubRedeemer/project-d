package com.jiubredeemer.app.itemstorage.inventory.dto.item

import com.jiubredeemer.app.itemstorage.inventory.dto.common.ItemSubTypeEnum
import com.jiubredeemer.app.itemstorage.inventory.dto.common.ItemTypeEnum
import com.jiubredeemer.app.itemstorage.inventory.dto.common.MultilingualField
import com.jiubredeemer.app.itemstorage.inventory.dto.common.RarityEnum
import java.sql.Timestamp
import java.util.*

data class ItemDto(
    val id: UUID?,
    val name: MultilingualField?,
    val type: ItemTypeEnum?,
    val typeName: String? = type?.apiName,
    val subtype: ItemSubTypeEnum?,
    val subtypeName: String? = subtype?.apiName,
    val customization: Boolean?,
    val rarity: RarityEnum?,
    val description: String?,
    val stats: ItemStatsDto?,
    val createdAt: Timestamp?,
    val roomId: UUID?,
    val creatorId: UUID?,
    val imgUrl: String?
)
