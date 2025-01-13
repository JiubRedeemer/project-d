package com.jiubredeemer.app.invite.controller

import com.jiubredeemer.app.invite.model.request.InviteChangeStatusRequest
import com.jiubredeemer.app.invite.model.request.InviteUserToRoomRequest
import com.jiubredeemer.app.invite.model.response.RoomUserInviteCountResponse
import com.jiubredeemer.app.invite.model.response.RoomUserInviteShortResponse
import com.jiubredeemer.app.invite.service.InviteApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/invites")
@Tag(name = "Приглашения", description = "API для управления приглашениями")
class InviteApiController(private val inviteApiService: InviteApiService) {

    @Operation(summary = "Получить список входящих приглашений")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Список входящих приглашений",
                content = [Content(schema = Schema(implementation = RoomUserInviteShortResponse::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/rooms")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getIncomingInvites(): List<RoomUserInviteShortResponse> {
        return inviteApiService.getIncomingInvites()
    }

    @Operation(summary = "Получить количество входящих приглашений")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Количество входящих приглашений",
                content = [Content(schema = Schema(implementation = RoomUserInviteCountResponse::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/rooms/count")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getIncomingInvitesCount(): RoomUserInviteCountResponse {
        return inviteApiService.getIncomingInvitesCount()
    }

    @Operation(summary = "Пригласить пользователя в комнату")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Приглашение успешно отправлено",
                content = [Content(schema = Schema(implementation = Boolean::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PostMapping("/rooms")
    @HasRoleOrThrow("ADMIN", "USER")
    fun inviteToRoom(@RequestBody request: InviteUserToRoomRequest): Boolean {
        return inviteApiService.inviteUserToRoom(request)
    }

    @Operation(summary = "Принять приглашение в комнату")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Приглашение успешно принято",
                content = [Content(schema = Schema(implementation = Boolean::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PostMapping("/rooms/accept")
    @HasRoleOrThrow("ADMIN", "USER")
    fun acceptInviteToRoom(@RequestBody request: InviteChangeStatusRequest): Boolean {
        return inviteApiService.acceptInviteToRoom(request)
    }

    @Operation(summary = "Отклонить приглашение в комнату")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Приглашение успешно отклонено",
                content = [Content(schema = Schema(implementation = Boolean::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PostMapping("/rooms/decline")
    @HasRoleOrThrow("ADMIN", "USER")
    fun declineInviteToRoom(@RequestBody request: InviteChangeStatusRequest): Boolean {
        return inviteApiService.declineInviteToRoom(request)
    }

    @Operation(summary = "Отозвать приглашение в комнату")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Приглашение успешно отозвано",
                content = [Content(schema = Schema(implementation = Boolean::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PostMapping("/rooms/revoke")
    @HasRoleOrThrow("ADMIN", "USER")
    fun revokeInviteToRoom(@RequestBody request: InviteChangeStatusRequest): Boolean {
        return inviteApiService.revokeInviteToRoom(request)
    }
}
