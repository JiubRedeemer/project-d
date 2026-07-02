package com.jiubredeemer.app.room.model.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.UUID

data class RoomPublicResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val filePath: String?,
    val memberCount: Int,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val nextSessionAt: LocalDateTime? = null,
)
