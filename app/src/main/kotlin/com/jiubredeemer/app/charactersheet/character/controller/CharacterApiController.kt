package com.jiubredeemer.app.charactersheet.character.controller

import com.jiubredeemer.app.charactersheet.character.dto.CharacterDto
import com.jiubredeemer.app.integration.charactersheet.dto.character.CreateCharacterRequest
import com.jiubredeemer.app.charactersheet.character.service.CharacterApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Персонажи", description = "API для управления персонажами")
class CharacterApiController(
    private val characterApiService: CharacterApiService
) {


    @PutMapping("/{roomId}/characters")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createCharacter(
        @PathVariable roomId: UUID,
        @RequestBody createCharacterRequest: CreateCharacterRequest
    ): CharacterDto? {
        return characterApiService.createCharacter(roomId, createCharacterRequest)
    }
}
