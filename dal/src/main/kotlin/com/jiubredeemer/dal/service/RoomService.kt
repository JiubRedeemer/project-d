package com.jiubredeemer.dal.service

import com.jiubredeemer.dal.entities.Room
import com.jiubredeemer.dal.entities.RoomUser
import com.jiubredeemer.dal.entities.RoomsUserKey
import com.jiubredeemer.dal.entities.User
import com.jiubredeemer.dal.repository.RoomRepository
import com.jiubredeemer.dal.repository.RoomsUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val roomsUserRepository: RoomsUserRepository
) {

    @Transactional
    fun create(room: Room, user: User): Room {
        val createdRoom = roomRepository.save<Room>(room)
        createDefaultRoomUser(user, createdRoom)
        return createdRoom
    }

    private fun createDefaultRoomUser(user: User, createdRoom: Room) {
        val userRoomToCreate = RoomUser()
        userRoomToCreate.user = user
        userRoomToCreate.room = createdRoom
        val key = RoomsUserKey();
        key.roomId = createdRoom.id
        key.userId = user.id
        userRoomToCreate.id = key
        userRoomToCreate.roles = listOf(RoomUser.Role.MASTER)
        roomsUserRepository.save(userRoomToCreate)
    }
}
