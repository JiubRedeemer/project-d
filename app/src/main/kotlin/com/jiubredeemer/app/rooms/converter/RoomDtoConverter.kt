package com.jiubredeemer.app.rooms.converter

import com.jiubredeemer.app.rooms.model.request.CreateRoomRequest
import com.jiubredeemer.dal.entities.Room
import com.jiubredeemer.dal.entities.User
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class RoomDtoConverter {
    fun createRequestToRoom(createRoomRequest: CreateRoomRequest, user: User): Room {
        val now = Timestamp.valueOf(LocalDateTime.now())
        val entity = Room()
        entity.name = createRoomRequest.name
        entity.owner = user
        entity.createDatetime = now
        entity.updateDatetime = now
        entity.lastActivityDatetime = now
        return entity
    }
}
