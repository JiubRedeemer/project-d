package com.jiubredeemer.app.rooms.converter

import com.jiubredeemer.app.rooms.exception.BrokenRoomException
import com.jiubredeemer.app.rooms.model.request.CreateRoomRequest
import com.jiubredeemer.app.rooms.model.response.RoomShortResponse
import com.jiubredeemer.dal.models.RoomDto
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
