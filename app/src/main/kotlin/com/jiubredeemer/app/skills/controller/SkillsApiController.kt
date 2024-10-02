package com.jiubredeemer.app.skills.controller

import com.jiubredeemer.app.races.model.RaceCreateInfoDto
import com.jiubredeemer.app.skills.model.SkillResponse
import com.jiubredeemer.app.skills.service.SkillApiService
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
@Tag(name = "Навыки", description = "API для управления навыками")
class SkillsApiController(
    private val skillApiService: SkillApiService
) {

    @Operation(summary = "Получить список названий и описаний навыков для комнаты")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Список навыков доступных для комнаты",
                content = [Content(schema = Schema(implementation = RaceCreateInfoDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/skills")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSkills(@PathVariable roomId: UUID): List<SkillResponse> {
        return skillApiService.getSkills(roomId)
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
    @GetMapping("/{roomId}/skills/byCode/{code}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSkillByCode(@PathVariable roomId: UUID, @PathVariable code: String): SkillResponse {
        return skillApiService.getSkillByCode(roomId, code)
    }

    @Operation(summary = "Получить список названий и описаний навыков для комнаты по коду класса")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Список навыков доступных для класса",
                content = [Content(schema = Schema(implementation = RaceCreateInfoDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/skills/byClass/{classCode}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSkillsByClass(@PathVariable roomId: UUID, @PathVariable classCode: String): List<SkillResponse> {
        return skillApiService.getSkillByClass(roomId, classCode)
    }

}
