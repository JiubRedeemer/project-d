package com.jiubredeemer.app.rooms.model.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на создание комнаты")
data class CreateRoomRequest(
    @Schema(description = "Название комнаты", example = "Conference Room")
    val name: String? = null,
)
