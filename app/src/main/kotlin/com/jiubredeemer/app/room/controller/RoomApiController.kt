package com.jiubredeemer.app.room.controller

import com.jiubredeemer.app.integration.dto.RuleTypeEnum
import com.jiubredeemer.app.room.model.request.CreateRoomRequest
import com.jiubredeemer.app.room.model.response.CreateRoomResponse
import com.jiubredeemer.app.room.model.response.RoomMasterResponse
import com.jiubredeemer.app.room.model.response.RoomShortResponse
import com.jiubredeemer.app.room.service.RoomApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import com.jiubredeemer.dal.entity.RoomUser
import com.jiubredeemer.dal.model.RoomDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

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
        if (RuleTypeEnum.HOMEBREW.name == request.rules?.name) {
            if (request.baseRules == null) {
                throw IllegalArgumentException("Укажи базовый тип правил, иначе нихера работать не будет")
            }
        } else {
            request.baseRules = request.rules
        }
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

    @Operation(summary = "Удалить комнату")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Комната удалена",
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @DeleteMapping("/{roomId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun deleteRoom(@PathVariable roomId: UUID) {
        roomApiService.deleteRoom(roomId)
    }

    @Operation(summary = "Получить список ролей пользователя в комнате")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Список ролей успешно получен",
                content = [Content(schema = Schema(implementation = RoomUser.Role::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/roles")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getRoleInRoom(@PathVariable("roomId") roomId: UUID): List<RoomUser.Role> {
        return roomApiService.getRoleInRoom(roomId)
    }

    @Operation(summary = "Получить информацию о комнате")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Информация о комнате успешно получена",
                content = [Content(schema = Schema(implementation = RoomMasterResponse::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getRoomInfo(@PathVariable("roomId") roomId: UUID): RoomMasterResponse? {
        return roomApiService.getRoomInfo(roomId);
    }
}
