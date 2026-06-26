package com.jiubredeemer.app.charactersheet.companion.controller

import com.jiubredeemer.app.charactersheet.companion.dto.CompanionDto
import com.jiubredeemer.app.charactersheet.companion.service.CompanionApiService
import com.jiubredeemer.app.integration.charactersheet.dto.companion.SaveCompanionRequest
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/rooms/{roomId}/characters/{characterId}/companions")
@Tag(name = "Companions", description = "API for companions (character scoped)")
class CompanionApiController(
    private val companionApiService: CompanionApiService,
) {
    @PutMapping("")
    @HasRoleOrThrow("ADMIN", "USER")
    fun saveCompanion(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @RequestBody request: SaveCompanionRequest,
    ): CompanionDto? {
        return companionApiService.saveCompanion(roomId, characterId, request)
    }

    @GetMapping("")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getCompanionsByCharacterId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): List<CompanionDto>? {
        return companionApiService.getCompanionsByCharacterId(roomId, characterId)
    }

    @GetMapping("/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getCompanionById(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable id: UUID,
    ): CompanionDto {
        return companionApiService.getCompanionById(roomId, id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Companion not found")
    }

    @PatchMapping("/{id}/hp/current")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateCurrentHp(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable id: UUID,
        @RequestBody request: UpdateCurrentHpRequest,
    ) {
        companionApiService.updateCurrentHp(roomId, id, request.value)
    }

    @PostMapping("/{id}/hp/restore")
    @HasRoleOrThrow("ADMIN", "USER")
    fun restoreFullHp(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable id: UUID,
    ) {
        companionApiService.restoreFullHp(roomId, id)
    }

    @DeleteMapping("/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun deleteCompanion(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable id: UUID,
    ) {
        companionApiService.deleteCompanion(roomId, id)
    }

    data class UpdateCurrentHpRequest(val value: Int)
}
