package com.jiubredeemer.app.rooms.controller

import com.jiubredeemer.app.rooms.model.request.CreateRoomRequest
import com.jiubredeemer.app.rooms.model.response.CreateRoomResponse
import com.jiubredeemer.app.rooms.model.response.RoomShortResponse
import com.jiubredeemer.app.rooms.service.RoomApiService
import com.jiubredeemer.auth.annotations.HasRoleOrThrow
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Комнаты", description = "API для управления комнатами")
class RoomApiController(
    private val roomApiService: RoomApiService,
) {

    @Operation(summary = "Создание новой комнаты")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Комната успешно создана",
                content = [Content(schema = Schema(implementation = CreateRoomResponse::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PutMapping
    @HasRoleOrThrow("ADMIN", "USER")
    fun createRoom(@RequestBody request: CreateRoomRequest): CreateRoomResponse {
        return roomApiService.create(request)
    }

    @Operation(summary = "Получить все комнаты текущего пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Список комнат успешно получен",
                content = [Content(schema = Schema(implementation = RoomShortResponse::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping()
    @HasRoleOrThrow("ADMIN", "USER")
    fun readAllRoomsForCurrentUser(): List<RoomShortResponse> {
        return roomApiService.readAllForCurrentUser()
    }


}
