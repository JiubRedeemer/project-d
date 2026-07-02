package com.jiubredeemer.app.room.model.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

data class RoomShortResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val filePath: String?,
    val lastActivityDate: Timestamp,
    val isPublic: Boolean = false,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val nextSessionAt: LocalDateTime? = null,
)
