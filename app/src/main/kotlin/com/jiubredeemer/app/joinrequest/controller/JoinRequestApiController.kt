package com.jiubredeemer.app.joinrequest.controller

import com.jiubredeemer.app.joinrequest.model.JoinRequestCountResponse
import com.jiubredeemer.app.joinrequest.model.JoinRequestResponse
import com.jiubredeemer.app.joinrequest.service.JoinRequestApiService
import com.jiubredeemer.app.room.model.response.RoomPublicResponse
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@Tag(name = "Публичные комнаты", description = "API для публичных комнат и заявок на вступление")
class JoinRequestApiController(private val joinRequestApiService: JoinRequestApiService) {

    @GetMapping("/api/rooms/public")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getPublicRooms(@RequestParam(required = false) search: String?): List<RoomPublicResponse> =
        joinRequestApiService.getPublicRooms(search)

    @PostMapping("/api/rooms/{roomId}/join-requests")
    @HasRoleOrThrow("ADMIN", "USER")
    fun requestToJoin(@PathVariable roomId: UUID): JoinRequestResponse =
        joinRequestApiService.requestToJoin(roomId)

    @GetMapping("/api/join-requests/incoming")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getIncomingRequests(): List<JoinRequestResponse> =
        joinRequestApiService.getIncomingRequests()

    @GetMapping("/api/join-requests/incoming/count")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getIncomingCount(): JoinRequestCountResponse =
        joinRequestApiService.getIncomingCount()

    @PostMapping("/api/join-requests/{requestId}/accept")
    @HasRoleOrThrow("ADMIN", "USER")
    fun accept(@PathVariable requestId: UUID) =
        joinRequestApiService.accept(requestId)

    @PostMapping("/api/join-requests/{requestId}/decline")
    @HasRoleOrThrow("ADMIN", "USER")
    fun decline(@PathVariable requestId: UUID) =
        joinRequestApiService.decline(requestId)
}
