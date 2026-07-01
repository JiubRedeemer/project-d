package com.jiubredeemer.app.itemstorage.inventory.dto.item

import com.fasterxml.jackson.annotation.JsonFormat
import com.jiubredeemer.app.itemstorage.inventory.dto.common.ItemSubTypeEnum
import com.jiubredeemer.app.itemstorage.inventory.dto.common.ItemTypeEnum
import com.jiubredeemer.app.itemstorage.inventory.dto.common.MultilingualField
import com.jiubredeemer.app.itemstorage.inventory.dto.common.RarityEnum
import java.time.LocalDateTime
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    var createdAt: LocalDateTime?,
    var roomId: UUID?,
    var creatorId: UUID?,
    var imgUrl: String?,
    var visibleForPlayers: Boolean?,
    var creator: String?
)
