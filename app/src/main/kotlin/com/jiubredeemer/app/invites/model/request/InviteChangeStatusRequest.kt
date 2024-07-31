package com.jiubredeemer.app.invites.model.request

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Запрос для изменения статуса приглашения")
data class InviteChangeStatusRequest(
    @Schema(description = "UUID комнаты", example = "123e4567-e89b-12d3-a456-426614174000")
    val roomId: UUID
)
