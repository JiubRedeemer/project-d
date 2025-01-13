package com.jiubredeemer.app.room.service

import com.jiubredeemer.dal.entity.RoomUser
import com.jiubredeemer.dal.repository.RoomsUserRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import java.util.*

@Component
class RoomAccessChecker(private val roomsUserRepository: RoomsUserRepository) {
    fun hasAccessOrThrow(roomId: UUID, userId: UUID): List<RoomUser.Role> {
        val roomUser = roomsUserRepository.findByRoomAndUser(roomId, userId)
        if (roomUser != null) {
            return roomUser.roles!!
        } else {
            throw AccessDeniedException("User has not allowed to access for this room")
        }
    }
}
