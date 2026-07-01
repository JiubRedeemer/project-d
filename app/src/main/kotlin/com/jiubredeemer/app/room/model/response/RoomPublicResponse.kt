package com.jiubredeemer.app.room.model.response

import java.util.UUID

data class RoomPublicResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val filePath: String?,
    val memberCount: Int
)
