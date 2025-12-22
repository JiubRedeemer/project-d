package com.jiubredeemer.app.itemstorage.inventory.dto.item

import com.jiubredeemer.app.itemstorage.inventory.dto.common.ItemSubTypeEnum
import com.jiubredeemer.app.itemstorage.inventory.dto.common.ItemTypeEnum
import com.jiubredeemer.app.itemstorage.inventory.dto.common.MultilingualField
import com.jiubredeemer.app.itemstorage.inventory.dto.common.RarityEnum
import java.sql.Timestamp
import java.util.*

data class ItemDto(
    var id: UUID?,
    var name: MultilingualField?,
    var type: ItemTypeEnum?,
    var typeName: String? = type?.apiName,
    var subtype: ItemSubTypeEnum?,
    var subtypeName: String? = subtype?.apiName,
    var customization: Boolean?,
    var rarity: RarityEnum?,
    var description: String?,
    var stats: ItemStatsDto?,
    var skills: List<ItemSkillsDto>?,
    var createdAt: Timestamp?,
    var roomId: UUID?,
    var creatorId: UUID?,
    var imgUrl: String?,
    var visibleForPlayers: Boolean?,
    var creator: String?
)
