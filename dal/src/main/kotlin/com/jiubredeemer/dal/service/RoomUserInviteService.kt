package com.jiubredeemer.dal.service

import com.jiubredeemer.common.exceptions.NotFoundException
import com.jiubredeemer.dal.converter.RoomUserInviteConverter
import com.jiubredeemer.dal.entities.RoomUser
import com.jiubredeemer.dal.entities.RoomUserInvite
import com.jiubredeemer.dal.models.RoomUserInviteDto
import com.jiubredeemer.dal.repository.RoomRepository
import com.jiubredeemer.dal.repository.RoomUserInviteRepository
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class RoomUserInviteService(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val roomUserInviteConverter: RoomUserInviteConverter,
    private val roomUserInviteRepository: RoomUserInviteRepository,
    private val roomService: RoomService
) {
    @Transactional
    fun createRoomUserInvite(roomUserInviteDto: RoomUserInviteDto) {
        val room = roomRepository.findById(roomUserInviteDto.roomId!!)
            .orElseThrow { NotFoundException("Room does not exist") }
        val owner = userRepository.findById(roomUserInviteDto.ownerId!!)
            .orElseThrow { NotFoundException("Room owner user does not exist") }
        val invitedUser = userRepository.findById(roomUserInviteDto.invitedUserId!!)
            .orElseThrow { NotFoundException("Invited user does not exist") }
        val invite = roomUserInviteConverter.toEntity(room, owner, invitedUser)
        invite.status = RoomUserInvite.Status.PENDING

        roomUserInviteRepository.save(invite)
    }

    @Transactional
    fun acceptRoomUserInvite(invitedUserId: UUID, roomId: UUID, role: RoomUser.Role) {
        val invite = roomUserInviteRepository.findByRoomIdAndInvitedUserId(invitedUserId, roomId)
            .orElseThrow { throw NotFoundException("Room invite not found") }
        if (invite.status != RoomUserInvite.Status.PENDING) {
            throw IllegalStateException("Room invite was already processed")
        }
        invite.status = RoomUserInvite.Status.ACCEPTED

        roomService.addUser(roomId, invitedUserId, Collections.singletonList(role))

        roomUserInviteRepository.save(invite)
    }

    @Transactional
    fun declineRoomUserInvite(invitedUserId: UUID, roomId: UUID) {
        val invite = roomUserInviteRepository.findByRoomIdAndInvitedUserId(invitedUserId, roomId)
            .orElseThrow { throw NotFoundException("Room invite not found") }
        if (invite.status != RoomUserInvite.Status.PENDING) {
            throw IllegalStateException("Room invite was already processed")
        }
        invite.status = RoomUserInvite.Status.DECLINED

        roomUserInviteRepository.save(invite)
    }

    @Transactional
    fun revokeRoomUserInvite(invitedUserId: UUID, roomId: UUID) {
        val invite = roomUserInviteRepository.findByRoomIdAndInvitedUserId(invitedUserId, roomId)
            .orElseThrow { throw NotFoundException("Room invite not found") }
        if (invite.status != RoomUserInvite.Status.PENDING) {
            throw IllegalStateException("Room invite was already processed")
        }
        invite.status = RoomUserInvite.Status.REVOKED

        roomUserInviteRepository.save(invite)
    }
}
