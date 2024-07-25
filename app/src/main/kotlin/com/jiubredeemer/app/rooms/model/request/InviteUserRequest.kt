package com.jiubredeemer.app.rooms.model.request

import com.jiubredeemer.dal.entities.RoomUser
import java.util.*

data class InviteUserRequest(val email: String, val roomId: UUID, val role: RoomUser.Role)
