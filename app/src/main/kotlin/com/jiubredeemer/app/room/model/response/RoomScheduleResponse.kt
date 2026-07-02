package com.jiubredeemer.app.room.model.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.time.LocalTime

data class RoomScheduleResponse(
    val isRecurring: Boolean,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val sessionDatetime: LocalDateTime? = null,
    val recurrenceType: String? = null,
    val dayOfWeek: Int? = null,
    val dayOfMonth: Int? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    val sessionTime: LocalTime? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val nextSessionAt: LocalDateTime? = null,
)
