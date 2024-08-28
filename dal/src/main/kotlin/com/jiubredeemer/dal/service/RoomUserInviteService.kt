package com.jiubredeemer.dal.service

import com.jiubredeemer.common.exceptions.NotFoundException
import com.jiubredeemer.dal.converter.RoomUserInviteConverter
import com.jiubredeemer.dal.entities.RoomUserInvite
import com.jiubredeemer.dal.entities.User
import com.jiubredeemer.dal.models.RoomUserInviteDto
import com.jiubredeemer.dal.repository.RoomRepository
import com.jiubredeemer.dal.repository.RoomUserInviteRepository
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Service
class RoomUserInviteService(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val roomUserInviteConverter: RoomUserInviteConverter,
    private val roomUserInviteRepository: RoomUserInviteRepository,
    private val roomService: RoomService,
) {
    @Transactional
    fun getIncomingInvites(userId: UUID): List<RoomUserInviteDto> {
        return roomUserInviteRepository.findPendingByInvitedUserId(userId).map {
            roomUserInviteConverter.toDto(it)
        }
    }

    @Transactional
    fun getIncomingInvitesCount(userId: UUID): Long {
        return roomUserInviteRepository.countPendingByInvitedUserId(userId)
    }


    @Transactional
    fun createRoomUserInvite(roomUserInviteDto: RoomUserInviteDto) {
        val now = Timestamp.valueOf(LocalDateTime.now())

        val room = roomRepository.findById(roomUserInviteDto.roomId!!)
            .orElseThrow { NotFoundException("Room does not exist") }
        val owner = userRepository.findById(roomUserInviteDto.ownerId!!)
            .orElseThrow { NotFoundException("Room owner user does not exist") }
        val invitedUser: User = userRepository.findByEmail(roomUserInviteDto.invitedUserEmail!!)
            ?: throw NotFoundException("Invited user does not exist")
        val invite = roomUserInviteConverter.toEntity(room, owner, invitedUser, roomUserInviteDto.role!!)
        invite.status = RoomUserInvite.Status.PENDING
        invite.createDatetime = now

        roomUserInviteRepository.save(invite)
    }

    @Transactional
    fun acceptRoomUserInvite(inviteId: UUID) {
        val invite = roomUserInviteRepository.findById(inviteId)
            .orElseThrow { throw NotFoundException("Room invite not found") }
        if (invite.status != RoomUserInvite.Status.PENDING) {
            throw IllegalStateException("Room invite was already processed")
        }
        invite.status = RoomUserInvite.Status.ACCEPTED

        roomService.addUser(invite.room!!.id!!, invite.invitedUser!!.id!!, Collections.singletonList(invite.role!!))

        roomUserInviteRepository.save(invite)
    }

    @Transactional
    fun declineRoomUserInvite(inviteId: UUID) {
        val invite = roomUserInviteRepository.findById(inviteId)
            .orElseThrow { throw NotFoundException("Room invite not found") }
        if (invite.status != RoomUserInvite.Status.PENDING) {
            throw IllegalStateException("Room invite was already processed")
        }
        invite.status = RoomUserInvite.Status.DECLINED

        roomUserInviteRepository.save(invite)
    }

    @Transactional
    fun revokeRoomUserInvite(inviteId: UUID) {
        val invite = roomUserInviteRepository.findById(inviteId)
            .orElseThrow { throw NotFoundException("Room invite not found") }
        if (invite.status != RoomUserInvite.Status.PENDING) {
            throw IllegalStateException("Room invite was already processed")
        }
        invite.status = RoomUserInvite.Status.REVOKED

        roomUserInviteRepository.save(invite)
    }
}
