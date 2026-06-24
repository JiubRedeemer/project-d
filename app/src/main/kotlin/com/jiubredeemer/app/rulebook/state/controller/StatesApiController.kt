package com.jiubredeemer.app.rulebook.state.controller

import com.jiubredeemer.app.rulebook.race.model.RaceCreateInfoDto
import com.jiubredeemer.app.rulebook.state.model.StateDto
import com.jiubredeemer.app.rulebook.state.service.StatesApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
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
@Tag(name = "Состояния", description = "API для управления состояниями")
class StatesApiController(
    private val statesApiService: StatesApiService
) {

    @Operation(summary = "Получить список состояний и описаний состояний для комнаты")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Список состояний доступных для комнаты",
                content = [Content(schema = Schema(implementation = StateDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/states")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getStates(@PathVariable roomId: UUID): List<StateDto> {
        return statesApiService.getStates(roomId)
    }

    @Operation(summary = "Получить название и описание навыка для комнаты по коду навыка")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Навык по коду",
                content = [Content(schema = Schema(implementation = RaceCreateInfoDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/states/byCode/{code}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getStateByCode(@PathVariable roomId: UUID, @PathVariable code: String): StateDto {
        return statesApiService.getStateByCode(roomId, code)
    }
}
