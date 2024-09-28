package com.jiubredeemer.app.races.controller

import com.jiubredeemer.app.races.model.RaceCreateInfoDto
import com.jiubredeemer.app.races.service.RaceApiService
import com.jiubredeemer.auth.annotations.HasRoleOrThrow
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Расы", description = "API для управления расами")
class RacesApiController(
    private val raceApiService: RaceApiService
) {

    @Operation(summary = "Получить список названий и описаний рас для комнаты")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Список рас доступных для комнаты",
                content = [Content(schema = Schema(implementation = RaceCreateInfoDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/races/named")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getIncomingInvites(@PathVariable roomId: UUID): List<RaceCreateInfoDto> {
        return raceApiService.getRacesNameAndDescription(roomId)
    }

}
