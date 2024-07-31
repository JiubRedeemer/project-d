package com.jiubredeemer.app.invites.converter

import com.jiubredeemer.app.invites.exceptions.BrokenInviteException
import com.jiubredeemer.app.invites.model.request.InviteUserToRoomRequest
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
            roomUserInviteDto.ownerId ?: throw BrokenInviteException("Invite hasn't ownerId"),
            roomUserInviteDto.roomId ?: throw BrokenInviteException("Invite hasn't roomId")
        )
    }
}
