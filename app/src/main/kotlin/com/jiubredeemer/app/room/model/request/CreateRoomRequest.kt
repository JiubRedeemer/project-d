package com.jiubredeemer.app.room.model.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на создание комнаты")
data class CreateRoomRequest(
    @Schema(description = "Название комнаты", example = "Днд-шная")
    val name: String? = null,
    @Schema(description = "Описание комнаты", example = "Днд Олега")
    val description: String? = null,
)
