package com.jiubredeemer.app.invites.service

import com.jiubredeemer.app.invites.converter.RoomInviteConverter
import com.jiubredeemer.app.invites.model.request.InviteChangeStatusRequest
import com.jiubredeemer.app.invites.model.request.InviteUserToRoomRequest
import com.jiubredeemer.app.invites.model.response.RoomUserInviteShortResponse
import com.jiubredeemer.app.invites.validators.RoomUserInviteValidator
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.dal.service.RoomUserInviteService
import org.springframework.stereotype.Service

@Service
class InviteApiService(
    private val roomUserInviteService: RoomUserInviteService,
    private val roomInviteConverter: RoomInviteConverter,
    private val accessChecker: AccessChecker,
    private val validator: RoomUserInviteValidator
) {
    fun getIncomingInvites(): List<RoomUserInviteShortResponse> {
        return roomUserInviteService.getIncomingInvites(accessChecker.getCurrentUser().id!!)
            .map { roomInviteConverter.dtoToShortResponse(it) }
    }

    fun inviteUserToRoom(inviteUserToRoomRequest: InviteUserToRoomRequest): Boolean {
        validator.onInvite(inviteUserToRoomRequest)

        val inviteDto = roomInviteConverter.requestToDto(inviteUserToRoomRequest)
        inviteDto.ownerId = accessChecker.getCurrentUser().id
        roomUserInviteService.createRoomUserInvite(inviteDto)
        return true
    }

    fun acceptInviteToRoom(inviteChangeStatusRequest: InviteChangeStatusRequest): Boolean {
        validator.onAccept(inviteChangeStatusRequest)

        roomUserInviteService.acceptRoomUserInvite(inviteChangeStatusRequest.roomId)
        return true
    }

    fun declineInviteToRoom(inviteChangeStatusRequest: InviteChangeStatusRequest): Boolean {
        validator.onDecline(inviteChangeStatusRequest)

        roomUserInviteService.declineRoomUserInvite(inviteChangeStatusRequest.roomId)
        return true
    }

    fun revokeInviteToRoom(inviteChangeStatusRequest: InviteChangeStatusRequest): Boolean {
        validator.onRevoke(inviteChangeStatusRequest)

        roomUserInviteService.revokeRoomUserInvite(inviteChangeStatusRequest.roomId)
        return true
    }
}
