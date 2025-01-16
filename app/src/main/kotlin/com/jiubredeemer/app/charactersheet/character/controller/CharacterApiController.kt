package com.jiubredeemer.app.charactersheet.character.controller

import com.jiubredeemer.app.charactersheet.character.dto.CharacterDto
import com.jiubredeemer.app.charactersheet.character.service.CharacterApiService
import com.jiubredeemer.app.integration.charactersheet.dto.character.CreateCharacterRequest
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/rooms/{roomId}")
@Tag(name = "Персонажи", description = "API для управления персонажами")
class CharacterApiController(
    private val characterApiService: CharacterApiService
) {


    @PutMapping("/characters")
    @HasRoleOrThrow("ADMIN", "USER")
    fun create(
        @PathVariable roomId: UUID,
        @RequestBody createCharacterRequest: CreateCharacterRequest
    ): CharacterDto? {
        return characterApiService.createCharacter(roomId, createCharacterRequest)
    }

    @GetMapping("/characters")
    @HasRoleOrThrow("ADMIN", "USER")
    fun findAllByRoomIdAndUserId(
        @PathVariable roomId: UUID,
    ): List<CharacterDto>? {
        return characterApiService.findAllByRoomIdAndUserId(roomId)
    }

    @GetMapping("/characters/{characterId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun findByCharacterId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): CharacterDto? {
        return characterApiService.findByCharacterId(roomId, characterId)
    }

    @GetMapping("/characters/{characterId}/header")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getHeaderInfoByCharacterId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): CharacterDto? {
        return characterApiService.getHeaderInfoByCharacterId(roomId, characterId)
    }

    @GetMapping("/characters/{characterId}/subheader")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSubheaderInfoByCharacterId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): CharacterDto? {
        return characterApiService.getSubheaderInfoByCharacterId(roomId, characterId)
    }

    @GetMapping("/characters/{characterId}/abilities")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getAbilitiesAndSkillsInfoByCharacterId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): CharacterDto? {
        return characterApiService.getAbilitiesAndSkillsInfoByCharacterId(roomId, characterId)
    }
}
