package com.jiubredeemer.app.rulebook.background.controller

import com.jiubredeemer.app.integration.dto.RuleTypeEnum
import com.jiubredeemer.app.integration.rulebook.dto.background.BackgroundDto
import com.jiubredeemer.app.rulebook.background.service.BackgroundApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
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
@Tag(name = "Background", description = "API for backgrounds (D&D 2024)")
class BackgroundApiController(
    private val backgroundApiService: BackgroundApiService
) {
    @Operation(summary = "Get backgrounds for room")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "List of backgrounds for room",
                content = [Content(schema = Schema(implementation = BackgroundDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Access denied",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/backgrounds")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getBackgrounds(
        @PathVariable roomId: UUID,
        @RequestParam("forceRuleType", required = false) forceRuleTypeEnum: RuleTypeEnum?
    ): List<BackgroundDto> {
        return backgroundApiService.getBackgrounds(roomId, forceRuleTypeEnum)
    }

    @Operation(summary = "Create background")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Background created",
                content = [Content(schema = Schema(implementation = BackgroundDto::class))]
            ),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())]),
            ApiResponse(
                responseCode = "404",
                description = "Failed to create background",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PutMapping("/{roomId}/backgrounds")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createBackground(@PathVariable roomId: UUID, @RequestBody backgroundDto: BackgroundDto): BackgroundDto {
        return backgroundApiService.createBackground(roomId, backgroundDto)
    }

    @Operation(summary = "Get background by code")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Background by code",
                content = [Content(schema = Schema(implementation = BackgroundDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Access denied",
                content = [Content(schema = Schema())]
            ),
            ApiResponse(
                responseCode = "404", description = "Background not found",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/backgrounds/{code}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getBackgroundByCode(
        @PathVariable roomId: UUID, @PathVariable code: String,
        @RequestParam("forceRuleType", required = false) forceRuleTypeEnum: RuleTypeEnum?
    ): BackgroundDto {
        return backgroundApiService.getBackgroundByCode(roomId, code, forceRuleTypeEnum)
    }
}
