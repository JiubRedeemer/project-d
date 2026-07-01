package com.jiubredeemer.dal.service

import com.jiubredeemer.common.exception.NotFoundException
import com.jiubredeemer.dal.entity.RoomJoinRequest
import com.jiubredeemer.dal.repository.RoomJoinRequestRepository
import com.jiubredeemer.dal.repository.RoomRepository
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Service
class RoomJoinRequestService(
    private val roomJoinRequestRepository: RoomJoinRequestRepository,
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val roomService: RoomService,
) {

    @Transactional
    fun createRequest(roomId: UUID, requesterId: UUID): RoomJoinRequest {
        val room = roomRepository.findById(roomId).orElseThrow { NotFoundException("Room not found") }
        if (!room.isPublic) throw IllegalStateException("Room is not public")
        if (room.owner?.id == requesterId) throw IllegalStateException("Owner cannot join their own room")

        val existing = roomJoinRequestRepository.findPendingByRoomIdAndRequesterId(roomId, requesterId)
        if (existing != null) throw IllegalStateException("Join request already pending")

        val requester = userRepository.findById(requesterId).orElseThrow { NotFoundException("User not found") }
        val request = RoomJoinRequest().apply {
            this.room = room
            this.requester = requester
            this.status = RoomJoinRequest.Status.PENDING
            this.createDatetime = Timestamp.valueOf(LocalDateTime.now())
        }
        return roomJoinRequestRepository.save(request)
    }

    @Transactional
    fun accept(requestId: UUID, ownerId: UUID) {
        val request = roomJoinRequestRepository.findById(requestId).orElseThrow { NotFoundException("Join request not found") }
        if (request.room?.owner?.id != ownerId) throw IllegalStateException("Not the room owner")
        if (request.status != RoomJoinRequest.Status.PENDING) throw IllegalStateException("Request already processed")

        request.status = RoomJoinRequest.Status.ACCEPTED
        roomJoinRequestRepository.save(request)
        roomService.addUser(request.room!!.id!!, request.requester!!.id!!, listOf(com.jiubredeemer.dal.entity.RoomUser.Role.PLAYER))
    }

    @Transactional
    fun decline(requestId: UUID, ownerId: UUID) {
        val request = roomJoinRequestRepository.findById(requestId).orElseThrow { NotFoundException("Join request not found") }
        if (request.room?.owner?.id != ownerId) throw IllegalStateException("Not the room owner")
        if (request.status != RoomJoinRequest.Status.PENDING) throw IllegalStateException("Request already processed")

        request.status = RoomJoinRequest.Status.DECLINED
        roomJoinRequestRepository.save(request)
    }

    @Transactional(readOnly = true)
    fun getPendingForOwner(ownerId: UUID): List<RoomJoinRequest> =
        roomJoinRequestRepository.findPendingByRoomOwnerId(ownerId)

    @Transactional(readOnly = true)
    fun countPendingForOwner(ownerId: UUID): Long =
        roomJoinRequestRepository.countPendingByRoomOwnerId(ownerId)
}
