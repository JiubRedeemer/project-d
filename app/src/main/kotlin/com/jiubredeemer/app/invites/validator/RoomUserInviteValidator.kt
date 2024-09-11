package com.jiubredeemer.app.invites.validator

import com.jiubredeemer.app.invites.model.request.InviteChangeStatusRequest
import com.jiubredeemer.app.invites.model.request.InviteUserToRoomRequest
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exceptions.NotFoundException
import com.jiubredeemer.dal.repository.RoomRepository
import com.jiubredeemer.dal.repository.RoomUserInviteRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import java.util.*

@Component
class RoomUserInviteValidator(
    private val roomUserInviteRepository: RoomUserInviteRepository,
    private val roomRepository: RoomRepository,
    private val accessChecker: AccessChecker
) {

    fun onInvite(inviteUserToRoomRequest: InviteUserToRoomRequest) {
        validateInvited(inviteUserToRoomRequest.roomId, inviteUserToRoomRequest.email)
        validateMandatoryFields(inviteUserToRoomRequest)
        validateOwner(inviteUserToRoomRequest.roomId)
    }

    fun onAccept(inviteChangeStatusRequest: InviteChangeStatusRequest) {
        validateExists(inviteChangeStatusRequest)
        validateInvited(inviteChangeStatusRequest)
    }

    fun onDecline(inviteChangeStatusRequest: InviteChangeStatusRequest) {
        validateExists(inviteChangeStatusRequest)
        validateInvited(inviteChangeStatusRequest)
    }

    fun onRevoke(inviteChangeStatusRequest: InviteChangeStatusRequest) {
        validateExists(inviteChangeStatusRequest)
        validateOwner(inviteChangeStatusRequest.inviteId)
    }

    private fun validateExists(inviteChangeStatusRequest: InviteChangeStatusRequest) {
        roomUserInviteRepository.findById(inviteChangeStatusRequest.inviteId)
            .orElseThrow { throw NotFoundException("Room does not exists") }

    }

    private fun validateInvited(roomId: UUID, invitedUserEmail: String) {
        roomUserInviteRepository.findByRoomIdAndUserEmail(roomId, invitedUserEmail)
            .let { if (it != null) throw IllegalStateException("User already invited") }
    }

    private fun validateInvited(inviteChangeStatusRequest: InviteChangeStatusRequest) {
        roomUserInviteRepository.findByInviteIdAndInvitedUserId(
            inviteChangeStatusRequest.inviteId,
            accessChecker.getCurrentUser().id!!
        ) ?: throw AccessDeniedException("User has not access to this room")
    }

    private fun validateMandatoryFields(inviteUserToRoomRequest: InviteUserToRoomRequest) {
        if (inviteUserToRoomRequest.email.isEmpty()) {
            throw IllegalArgumentException("The 'email' field must not be null or empty")
        }
    }

    private fun validateOwner(roomId: UUID) {
        roomRepository.findByIdAndOwnerId(roomId, accessChecker.getCurrentUser().id!!)
            ?: throw AccessDeniedException("User is not owner of that room")
    }
}
