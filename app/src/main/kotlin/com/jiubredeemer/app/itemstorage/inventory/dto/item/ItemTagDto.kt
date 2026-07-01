package com.jiubredeemer.app.itemstorage.inventory.dto.item

import java.util.UUID

data class ItemTagDto(
    val id: UUID,
    val name: String,
    val description: String?,
    val roomId: UUID?
)
