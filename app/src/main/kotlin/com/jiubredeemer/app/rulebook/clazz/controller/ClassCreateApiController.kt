package com.jiubredeemer.app.rulebook.clazz.controller

import com.jiubredeemer.app.integration.rulebook.dto.clazz.ClazzDto
import com.jiubredeemer.app.rulebook.clazz.service.ClassApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api")
@Tag(name = "Class Controller", description = "API for class updates")
class ClassCreateApiController(
    private val classApiService: ClassApiService
) {
    @Operation(summary = "Set hidden flag for class")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Class hidden flag updated",
                content = [Content(schema = Schema(implementation = ClazzDto::class))]
            ),
            ApiResponse(responseCode = "400", description = "Invalid request", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())])
        ]
    )
    @PatchMapping("/classes/hidden/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun setClassHidden(@PathVariable id: UUID, @RequestParam hidden: Boolean): ClazzDto {
        return classApiService.setClassHidden(id, hidden)
    }
}
