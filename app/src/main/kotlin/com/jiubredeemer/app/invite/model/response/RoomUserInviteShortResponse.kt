package com.jiubredeemer.app.invite.model.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Короткая модель приглашения в комнату")
data class RoomUserInviteShortResponse(
    @Schema(description = "UUID приглашения")
    val id: UUID,
    @Schema(description = "Владелец приглашения")
    val owner: OwnerDto,
    @Schema(description = "Комната")
    val room: RoomDto
)
