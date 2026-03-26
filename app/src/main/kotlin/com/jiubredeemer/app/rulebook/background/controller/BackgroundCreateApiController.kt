package com.jiubredeemer.app.rulebook.background.controller

import com.jiubredeemer.app.integration.rulebook.dto.background.BackgroundDto
import com.jiubredeemer.app.rulebook.background.service.BackgroundApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Tag(name = "Background Controller", description = "API for background creation")
class BackgroundCreateApiController(
    private val backgroundApiService: BackgroundApiService
) {
    @Operation(summary = "Create background")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Background created",
                content = [Content(schema = Schema(implementation = BackgroundDto::class))]
            ),
            ApiResponse(responseCode = "400", description = "Invalid request", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())])
        ]
    )
    @PutMapping("/backgrounds")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createBackground(@RequestBody backgroundDto: BackgroundDto): BackgroundDto {
        return backgroundApiService.createBackground(backgroundDto)
    }
}
