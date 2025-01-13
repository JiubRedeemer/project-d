package com.jiubredeemer.app.room.model.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Ответ на запрос создания комнаты")
data class CreateRoomResponse(
    @Schema(description = "Идентификатор созданной комнаты", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    val id: UUID? = null
)
