package com.jiubredeemer.app.joinrequest.service

import com.jiubredeemer.app.joinrequest.model.*
import com.jiubredeemer.app.room.model.response.RoomPublicResponse
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.dal.entity.Room
import com.jiubredeemer.dal.entity.RoomJoinRequest
import com.jiubredeemer.dal.repository.RoomRepository
import com.jiubredeemer.dal.service.RoomJoinRequestService
import com.jiubredeemer.dal.service.RoomScheduleService
import org.springframework.stereotype.Service
import java.util.*

@Service
class JoinRequestApiService(
    private val roomJoinRequestService: RoomJoinRequestService,
    private val roomRepository: RoomRepository,
    private val accessChecker: AccessChecker,
    private val roomScheduleService: RoomScheduleService,
) {

    fun getPublicRooms(search: String?): List<RoomPublicResponse> {
        val userId = accessChecker.getCurrentUser().id!!
        val rows = roomRepository.findPublicRoomsExcludingMember(userId, search?.takeIf { it.isNotBlank() })
        val roomIds = rows.map { (it[0] as Room).id!! }
        val schedules = roomScheduleService.findByRoomIds(roomIds)
        return rows.map { row ->
            val room = row[0] as Room
            val memberCount = (row[1] as Long).toInt()
            val schedule = schedules[room.id!!]
            val nextSessionAt = schedule?.let { roomScheduleService.computeNextSessionAt(it) }
            RoomPublicResponse(
                id = room.id!!,
                name = room.name!!,
                description = room.description,
                filePath = room.filePath,
                memberCount = memberCount,
                nextSessionAt = nextSessionAt,
            )
        }
    }

    fun requestToJoin(roomId: UUID): JoinRequestResponse {
        val requesterId = accessChecker.getCurrentUser().id!!
        val request = roomJoinRequestService.createRequest(roomId, requesterId)
        return toResponse(request)
    }

    fun getIncomingRequests(): List<JoinRequestResponse> {
        val ownerId = accessChecker.getCurrentUser().id!!
        return roomJoinRequestService.getPendingForOwner(ownerId).map { toResponse(it) }
    }

    fun getIncomingCount(): JoinRequestCountResponse {
        val ownerId = accessChecker.getCurrentUser().id!!
        return JoinRequestCountResponse(roomJoinRequestService.countPendingForOwner(ownerId))
    }

    fun accept(requestId: UUID) {
        val ownerId = accessChecker.getCurrentUser().id!!
        roomJoinRequestService.accept(requestId, ownerId)
    }

    fun decline(requestId: UUID) {
        val ownerId = accessChecker.getCurrentUser().id!!
        roomJoinRequestService.decline(requestId, ownerId)
    }

    private fun toResponse(r: RoomJoinRequest) = JoinRequestResponse(
        id = r.id!!,
        room = JoinRequestRoomDto(r.room!!.id!!, r.room!!.name!!),
        requester = JoinRequestUserDto(r.requester!!.id!!, r.requester!!.username!!),
        status = r.status.name,
        createDatetime = r.createDatetime!!
    )
}
