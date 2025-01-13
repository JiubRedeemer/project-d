package com.jiubredeemer.app.rulebook.clazz.controller

import com.jiubredeemer.app.rulebook.clazz.model.ClassCreateInfoDto
import com.jiubredeemer.app.rulebook.clazz.service.ClassApiService
import com.jiubredeemer.app.rulebook.race.model.RaceCreateInfoDto
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
@Tag(name = "Классы", description = "API для управления классами")
class ClassApiController(
    private val classApiService: ClassApiService
) {

    @Operation(summary = "Получить список названий и описаний классов для комнаты")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Список классов доступных для комнаты",
                content = [Content(schema = Schema(implementation = RaceCreateInfoDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/classes")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getClasses(@PathVariable roomId: UUID): List<ClassCreateInfoDto> {
        return classApiService.getClasses(roomId)
    }

}
