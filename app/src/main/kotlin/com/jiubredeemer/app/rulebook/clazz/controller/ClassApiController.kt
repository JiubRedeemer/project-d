package com.jiubredeemer.app.rulebook.clazz.controller

import com.jiubredeemer.app.integration.rulebook.dto.clazz.ClazzGroupDto
import com.jiubredeemer.app.rulebook.clazz.model.ClassCreateInfoDto
import com.jiubredeemer.app.rulebook.clazz.service.ClassApiService
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
@Tag(name = "Classes", description = "API for classes")
class ClassApiController(
    private val classApiService: ClassApiService
) {
    @GetMapping("/{roomId}/classes/grouped")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getGroupedClasses(@PathVariable roomId: UUID): List<ClazzGroupDto> {
        return classApiService.getGroupedClasses(roomId)
    }

    @Operation(summary = "Get classes for room")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "List of classes for room",
                content = [Content(schema = Schema(implementation = ClassCreateInfoDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Access denied",
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
