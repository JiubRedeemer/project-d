package com.jiubredeemer.app.rulebook.race.controller

import com.jiubredeemer.app.integration.rulebook.dto.race.RaceDto
import com.jiubredeemer.app.rulebook.race.service.RaceApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api")
@Tag(name = "Race Controller", description = "API for race creation")
class RaceCreateApiController(
    private val raceApiService: RaceApiService
) {
    @Operation(summary = "Create race")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Race created",
                content = [Content(schema = Schema(implementation = RaceDto::class))]
            ),
            ApiResponse(responseCode = "400", description = "Invalid request", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())])
        ]
    )
    @PutMapping("/races")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createRace(@RequestBody raceDto: RaceDto): RaceDto {
        return raceApiService.createRace(raceDto)
    }

    @Operation(summary = "Set hidden flag for race")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Race hidden flag updated",
                content = [Content(schema = Schema(implementation = RaceDto::class))]
            ),
            ApiResponse(responseCode = "400", description = "Invalid request", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "403", description = "Access denied", content = [Content(schema = Schema())])
        ]
    )
    @PatchMapping("/races/hidden/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun setRaceHidden(@PathVariable id: UUID, @RequestParam hidden: Boolean): RaceDto {
        return raceApiService.setRaceHidden(id, hidden)
    }
}
