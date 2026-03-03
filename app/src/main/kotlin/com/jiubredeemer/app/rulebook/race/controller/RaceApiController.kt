package com.jiubredeemer.app.rulebook.race.controller

import com.jiubredeemer.app.integration.rulebook.dto.race.RaceDto
import com.jiubredeemer.app.integration.rulebook.dto.race.RaceGroupDto
import com.jiubredeemer.app.rulebook.race.model.RaceCreateInfoDto
import com.jiubredeemer.app.rulebook.race.service.RaceApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Races", description = "API for races")
class RaceApiController(
    private val raceApiService: RaceApiService
) {
    @GetMapping("/{roomId}/races/grouped")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getGroupedRaces(@PathVariable roomId: UUID): List<RaceGroupDto> {
        return raceApiService.getGroupedRaces(roomId)
    }

    @Operation(summary = "Get races for room")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "List of races for room",
                content = [Content(schema = Schema(implementation = RaceCreateInfoDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Access denied",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/races")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getRaces(@PathVariable roomId: UUID): List<RaceCreateInfoDto> {
        return raceApiService.getRaces(roomId)
    }

    @Operation(summary = "Create race")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Race created",
                content = [Content(schema = Schema(implementation = RaceDto::class))]
            ),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Failed to create race", content = [Content(schema = Schema())])
        ]
    )
    @PutMapping("/{roomId}/races")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createRace(@PathVariable roomId: UUID, @RequestBody raceDto: RaceDto): RaceDto {
        return raceApiService.createRace(roomId, raceDto)
    }

    @Operation(summary = "Get root races (species) for room")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "List of root races",
                content = [Content(schema = Schema(implementation = RaceDto::class))]
            ),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())])
        ]
    )
    @GetMapping("/{roomId}/races/root")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getRootRaces(@PathVariable roomId: UUID): List<RaceDto> {
        return raceApiService.getRootRaces(roomId)
    }

    @Operation(summary = "Get race by code")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Race by code",
                content = [Content(schema = Schema(implementation = RaceDto::class))]
            ),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Race not found", content = [Content(schema = Schema())])
        ]
    )
    @GetMapping("/{roomId}/races/{code}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getRaceByCode(@PathVariable roomId: UUID, @PathVariable code: String): RaceDto {
        return raceApiService.getRaceByCode(roomId, code)
    }

    @Operation(summary = "Get subspecies by race code")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "List of subspecies",
                content = [Content(schema = Schema(implementation = RaceDto::class))]
            ),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())])
        ]
    )
    @GetMapping("/{roomId}/races/{code}/subspecies")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getRaceSubspeciesByCode(@PathVariable roomId: UUID, @PathVariable code: String): List<RaceDto> {
        return raceApiService.getRaceSubspeciesByCode(roomId, code)
    }
}
