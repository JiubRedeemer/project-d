package com.jiubredeemer.app.rulebook.abilities.controller


import com.jiubredeemer.app.rulebook.abilities.dto.AbilityResponse
import com.jiubredeemer.app.rulebook.abilities.service.AbilityApiService
import com.jiubredeemer.app.rulebook.races.model.RaceCreateInfoDto
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
@Tag(name = "Характеристики", description = "API для управления характеристиками")
class AbilitiesApiController(
    private val abilityApiService: AbilityApiService
) {

    @Operation(summary = "Получить список характеристик для комнаты")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Список характеристик доступных для комнаты",
                content = [Content(schema = Schema(implementation = RaceCreateInfoDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/abilities")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getAbilities(@PathVariable roomId: UUID): List<AbilityResponse> {
        return abilityApiService.getAbilities(roomId)
    }

}
