package com.jiubredeemer.app.invite.model.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Модель количества приглашений в комнату")
data class RoomUserInviteCountResponse(
    @Schema(description = "UUID приглашения")
    val count: Long
)
