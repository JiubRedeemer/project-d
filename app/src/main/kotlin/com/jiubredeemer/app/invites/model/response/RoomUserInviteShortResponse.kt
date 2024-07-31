package com.jiubredeemer.app.invites.model.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Короткая модель приглашения в комнату")
data class RoomUserInviteShortResponse(
    @Schema(description = "UUID приглашения")
    val id: UUID,
    @Schema(description = "UUID владельца приглашения")
    val ownerId: UUID,
    @Schema(description = "UUID комнаты")
    val roomId: UUID
)
