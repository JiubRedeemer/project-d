package com.jiubredeemer.app.itemstorage.inventory.dto.item

import com.fasterxml.jackson.annotation.JsonInclude
import com.jiubredeemer.app.itemstorage.inventory.dto.common.MultilingualField
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ItemSkillsDto(
    val id: UUID?,
    val itemId: UUID?,
    val name: MultilingualField?,
    val description: String?,
    val shortDescription: String?,
    val charges: Int?,
    val castTime: String?,
    val distance: String?,
    val chargesRefill: ChargesRefillEnum?,
    val imgUrl: String?
)
