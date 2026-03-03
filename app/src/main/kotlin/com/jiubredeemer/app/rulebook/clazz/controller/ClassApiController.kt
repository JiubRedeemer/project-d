package com.jiubredeemer.app.rulebook.clazz.controller

import com.jiubredeemer.app.integration.dto.RuleTypeEnum
import com.jiubredeemer.app.integration.rulebook.dto.clazz.ClazzDto
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
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Classes", description = "API for classes")
class ClassApiController(
    private val classApiService: ClassApiService
) {
    @GetMapping("/{roomId}/classes/grouped")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getGroupedClasses(
        @PathVariable roomId: UUID,
        @RequestParam("forceRuleType", required = false) forceRuleTypeEnum: RuleTypeEnum?
    ): List<ClazzGroupDto> {
        return classApiService.getGroupedClasses(roomId, forceRuleTypeEnum)
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
    fun getClasses(
        @PathVariable roomId: UUID,
        @RequestParam("forceRuleType", required = false) forceRuleTypeEnum: RuleTypeEnum?
    ): List<ClassCreateInfoDto> {
        return classApiService.getClasses(roomId, forceRuleTypeEnum)
    }

    @Operation(summary = "Create class")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Class created",
                content = [Content(schema = Schema(implementation = ClazzDto::class))]
            ),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())]),
            ApiResponse(
                responseCode = "404",
                description = "Failed to create class",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PutMapping("/{roomId}/classes")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createClass(@PathVariable roomId: UUID, @RequestBody clazzDto: ClazzDto): ClazzDto {
        return classApiService.createClass(roomId, clazzDto)
    }

    @Operation(summary = "Get root classes for room")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "List of root classes",
                content = [Content(schema = Schema(implementation = ClazzDto::class))]
            ),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())])
        ]
    )
    @GetMapping("/{roomId}/classes/root")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getRootClasses(
        @PathVariable roomId: UUID,
        @RequestParam("forceRuleType", required = false) forceRuleTypeEnum: RuleTypeEnum?
    ): List<ClazzDto> {
        return classApiService.getRootClasses(roomId, forceRuleTypeEnum)
    }

    @Operation(summary = "Get class by code")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Class by code",
                content = [Content(schema = Schema(implementation = ClazzDto::class))]
            ),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Class not found", content = [Content(schema = Schema())])
        ]
    )
    @GetMapping("/{roomId}/classes/{code}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getClassByCode(
        @PathVariable roomId: UUID, @PathVariable code: String,
        @RequestParam("forceRuleType", required = false) forceRuleTypeEnum: RuleTypeEnum?
    ): ClazzDto {
        return classApiService.getClassByCode(roomId, code, forceRuleTypeEnum)
    }

    @Operation(summary = "Get subclasses by class code")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "List of subclasses",
                content = [Content(schema = Schema(implementation = ClazzDto::class))]
            ),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())])
        ]
    )
    @GetMapping("/{roomId}/classes/{code}/subclasses")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSubClassesByCode(
        @PathVariable roomId: UUID, @PathVariable code: String,
        @RequestParam("forceRuleType", required = false) forceRuleTypeEnum: RuleTypeEnum?
    ): List<ClazzDto> {
        return classApiService.getSubClassesForRoom(roomId, code, forceRuleTypeEnum)
    }
}
