package com.jiubredeemer.app.room.converter

import com.jiubredeemer.app.room.exception.BrokenRoomException
import com.jiubredeemer.app.room.model.request.CreateRoomRequest
import com.jiubredeemer.app.room.model.response.RoomShortResponse
import com.jiubredeemer.dal.model.RoomDto
import org.springframework.stereotype.Component

@Component
class RoomDtoConverter {
    fun createRequestToRoomDto(createRoomRequest: CreateRoomRequest): RoomDto {
        val roomDto = RoomDto()
        roomDto.name = createRoomRequest.name
        roomDto.description = createRoomRequest.description
        return roomDto
    }

    fun roomDtoToShortRoom(roomDto: RoomDto): RoomShortResponse {
        return RoomShortResponse(
            roomDto.id ?: throw BrokenRoomException("Room hasn't id"),
            roomDto.name ?: throw BrokenRoomException("Room hasn't name"),
            roomDto.description,
            roomDto.lastActivityDatetime ?: throw BrokenRoomException("Room hasn't lastActivityDatetime")
        )
    }
}
