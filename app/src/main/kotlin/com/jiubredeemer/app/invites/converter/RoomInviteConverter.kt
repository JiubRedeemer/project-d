package com.jiubredeemer.app.invites.converter

import com.jiubredeemer.app.invites.exceptions.BrokenInviteException
import com.jiubredeemer.app.invites.model.request.InviteUserToRoomRequest
import com.jiubredeemer.app.invites.model.response.OwnerDto
import com.jiubredeemer.app.invites.model.response.RoomUserInviteShortResponse
import com.jiubredeemer.dal.models.RoomUserInviteDto
import org.springframework.stereotype.Component

@Component
class RoomInviteConverter {

    fun requestToDto(
        inviteUserToRoomRequest: InviteUserToRoomRequest
    ): RoomUserInviteDto {
        val dto = RoomUserInviteDto()
        dto.roomId = inviteUserToRoomRequest.roomId
        dto.invitedUserEmail = inviteUserToRoomRequest.email
        dto.role = inviteUserToRoomRequest.role
        return dto
    }

    fun dtoToShortResponse(
        roomUserInviteDto: RoomUserInviteDto
    ): RoomUserInviteShortResponse {
        return RoomUserInviteShortResponse(
            roomUserInviteDto.id ?: throw BrokenInviteException("Invite hasn't id"),
            userDtoToInviteOwnerDto(roomUserInviteDto),
            roomDtoToInviteRoomDto(roomUserInviteDto)
        )
    }

    private fun roomDtoToInviteRoomDto(roomUserInviteDto: RoomUserInviteDto) =
        com.jiubredeemer.app.invites.model.response.RoomDto(
            roomUserInviteDto.roomId ?: throw BrokenInviteException("Room hasn't id"),
            roomUserInviteDto.roomName ?: throw BrokenInviteException("Room hasn't name"),
            roomUserInviteDto.roomDescription
        )

    private fun userDtoToInviteOwnerDto(roomUserInviteDto: RoomUserInviteDto) =
        OwnerDto(
            roomUserInviteDto.ownerId ?: throw BrokenInviteException("Owner hasn't id"),
            roomUserInviteDto.ownerUsername ?: throw BrokenInviteException("Owner hasn't username")
        )


}
