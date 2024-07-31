package com.jiubredeemer.app.invites.model.request

import com.jiubredeemer.dal.entities.RoomUser
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Модель для приглашения пользователя в комнату")
data class InviteUserToRoomRequest(
    @Schema(description = "Email приглашаемого пользователя", example = "user@example.com")
    val email: String,

    @Schema(description = "UUID комнаты", example = "123e4567-e89b-12d3-a456-426614174000")
    val roomId: UUID,

    @Schema(description = "Роль пользователя в комнату", example = "PLAYER")
    val role: RoomUser.Role
)
