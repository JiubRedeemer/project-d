package com.jiubredeemer.app.room.service

import com.jiubredeemer.app.room.model.request.RoomScheduleRequest
import com.jiubredeemer.app.room.model.response.RoomScheduleResponse
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import com.jiubredeemer.dal.entity.RoomUser
import com.jiubredeemer.dal.repository.RoomRepository
import com.jiubredeemer.dal.service.RoomScheduleService
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class RoomScheduleApiService(
    private val roomScheduleService: RoomScheduleService,
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val roomRepository: RoomRepository,
) {
    fun getSchedule(roomId: UUID): RoomScheduleResponse? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val schedule = roomScheduleService.findByRoomId(roomId) ?: return null
        return toResponse(schedule)
    }

    fun upsertSchedule(roomId: UUID, request: RoomScheduleRequest): RoomScheduleResponse {
        val roles = roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        if (!roles.contains(RoomUser.Role.MASTER)) {
            throw AccessDeniedException("Only room master can manage schedule")
        }
        val room = roomRepository.findById(roomId).orElseThrow { NotFoundException("Room not found") }
        val schedule = roomScheduleService.upsert(
            room = room,
            isRecurring = request.isRecurring,
            sessionDatetime = request.sessionDatetime,
            recurrenceType = request.recurrenceType,
            dayOfWeek = request.dayOfWeek,
            dayOfMonth = request.dayOfMonth,
            sessionTime = request.sessionTime,
        )
        return toResponse(schedule)
    }

    fun deleteSchedule(roomId: UUID) {
        val roles = roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        if (!roles.contains(RoomUser.Role.MASTER)) {
            throw AccessDeniedException("Only room master can manage schedule")
        }
        roomScheduleService.deleteByRoomId(roomId)
    }

    private fun toResponse(schedule: com.jiubredeemer.dal.entity.RoomSchedule) = RoomScheduleResponse(
        isRecurring = schedule.isRecurring,
        sessionDatetime = schedule.sessionDatetime,
        recurrenceType = schedule.recurrenceType,
        dayOfWeek = schedule.dayOfWeek,
        dayOfMonth = schedule.dayOfMonth,
        sessionTime = schedule.sessionTime,
        nextSessionAt = roomScheduleService.computeNextSessionAt(schedule),
    )
}
