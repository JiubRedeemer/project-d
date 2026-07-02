package com.jiubredeemer.app.room.controller

import com.jiubredeemer.app.room.model.request.RoomScheduleRequest
import com.jiubredeemer.app.room.model.response.RoomScheduleResponse
import com.jiubredeemer.app.room.service.RoomScheduleApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/rooms/{roomId}/schedule")
class RoomScheduleApiController(
    private val roomScheduleApiService: RoomScheduleApiService,
) {
    @GetMapping
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSchedule(@PathVariable roomId: UUID): ResponseEntity<RoomScheduleResponse> {
        val response = roomScheduleApiService.getSchedule(roomId)
        return if (response != null) ResponseEntity.ok(response) else ResponseEntity.noContent().build()
    }

    @PutMapping
    @HasRoleOrThrow("ADMIN", "USER")
    fun upsertSchedule(
        @PathVariable roomId: UUID,
        @RequestBody request: RoomScheduleRequest,
    ): RoomScheduleResponse = roomScheduleApiService.upsertSchedule(roomId, request)

    @DeleteMapping
    @HasRoleOrThrow("ADMIN", "USER")
    fun deleteSchedule(@PathVariable roomId: UUID) = roomScheduleApiService.deleteSchedule(roomId)
}
