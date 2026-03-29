package com.jiubredeemer.dal.service

import com.jiubredeemer.common.exception.NotFoundException
import com.jiubredeemer.dal.converter.RoomUserInviteConverter
import com.jiubredeemer.dal.entity.RoomUserInvite
import com.jiubredeemer.dal.entity.User
import com.jiubredeemer.dal.model.CreateRoomInviteResult
import com.jiubredeemer.dal.model.RoomUserInviteDto
import com.jiubredeemer.dal.repository.RoomRepository
import com.jiubredeemer.dal.repository.RoomUserInviteRepository
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
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
    private val secureRandom = SecureRandom()

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
    fun createRoomUserInvite(roomUserInviteDto: RoomUserInviteDto): CreateRoomInviteResult {
        val now = Timestamp.valueOf(LocalDateTime.now())

        val room = roomRepository.findById(roomUserInviteDto.roomId!!)
            .orElseThrow { NotFoundException("Room does not exist") }
        val owner = userRepository.findById(roomUserInviteDto.ownerId!!)
            .orElseThrow { NotFoundException("Room owner user does not exist") }
        val emailInput = roomUserInviteDto.invitedUserEmail!!.trim()
        val emailNorm = emailInput.lowercase(Locale.ROOT)
        val invitedUser: User? = userRepository.findByEmail(emailInput)
            ?: userRepository.findByEmail(emailNorm)

        if (invitedUser != null) {
            val invite = roomUserInviteConverter.toEntity(room, owner, invitedUser, roomUserInviteDto.role!!)
            invite.status = RoomUserInvite.Status.PENDING
            invite.createDatetime = now
            roomUserInviteRepository.save(invite)
            return CreateRoomInviteResult(
                emailDispatchNeeded = false,
                inviteToken = null,
                invitedEmail = emailInput,
                roomName = room.name.orEmpty(),
            )
        }

        val token = newInviteToken()
        val invite = roomUserInviteConverter.toEntityExternal(
            room,
            owner,
            emailNorm,
            roomUserInviteDto.role!!,
            token,
        )
        invite.status = RoomUserInvite.Status.PENDING
        invite.createDatetime = now
        roomUserInviteRepository.save(invite)
        return CreateRoomInviteResult(
            emailDispatchNeeded = true,
            inviteToken = token,
            invitedEmail = emailInput,
            roomName = room.name.orEmpty(),
        )
    }

    @Transactional
    fun claimPendingInviteByToken(token: String, newUserId: UUID, registrationEmail: String) {
        val trimmedToken = token.trim()
        val invite = roomUserInviteRepository.findPendingByInviteToken(trimmedToken)
            ?: throw IllegalArgumentException("Invalid or expired room invite")
        val regNorm = registrationEmail.trim().lowercase(Locale.ROOT)
        if (invite.invitedEmail != regNorm) {
            throw IllegalArgumentException("Registration email does not match the invited address")
        }
        val user = userRepository.findById(newUserId).orElseThrow { NotFoundException("User not found") }
        invite.invitedUser = user
        invite.inviteToken = null
        invite.status = RoomUserInvite.Status.ACCEPTED
        roomService.addUser(invite.room!!.id!!, user.id!!, Collections.singletonList(invite.role!!))
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

    private fun newInviteToken(): String {
        val bytes = ByteArray(32)
        secureRandom.nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }
}
