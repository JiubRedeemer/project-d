package com.jiubredeemer.app.charactersheet.npc.controller

import com.jiubredeemer.app.charactersheet.npc.dto.CharacterNpcRelationDto
import com.jiubredeemer.app.charactersheet.npc.dto.NpcDto
import com.jiubredeemer.app.charactersheet.npc.dto.RelationTypeEnum
import com.jiubredeemer.app.charactersheet.npc.service.CharacterNpcRelationApiService
import com.jiubredeemer.app.integration.charactersheet.dto.npc.SaveCharacterNpcRelationRequest
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/rooms/{roomId}/characters/{characterId}/npcs")
@Tag(name = "Character-NPC Relations", description = "API for character–NPC relations (room scoped)")
class CharacterNpcRelationApiController(
    private val characterNpcRelationApiService: CharacterNpcRelationApiService,
) {
    @PutMapping("/relations")
    @HasRoleOrThrow("ADMIN", "USER")
    fun saveCharacterNpcRelationForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @RequestBody request: SaveCharacterNpcRelationRequest,
    ): CharacterNpcRelationDto? {
        return characterNpcRelationApiService.saveCharacterNpcRelationForRoom(roomId, characterId, request)
    }

    @DeleteMapping("/relations/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun deleteCharacterNpcRelationByIdForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable id: UUID,
    ) {
        characterNpcRelationApiService.deleteCharacterNpcRelationByIdForRoom(roomId, characterId, id)
    }

    @GetMapping("")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getNpcsByCharacterIdForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): List<NpcDto>? {
        return characterNpcRelationApiService.getNpcsByCharacterIdForRoom(roomId, characterId)
    }

    @GetMapping("/relationType/{relationType}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getNpcsByCharacterIdAndRelationTypeForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable relationType: RelationTypeEnum,
    ): List<NpcDto>? {
        return characterNpcRelationApiService.getNpcsByCharacterIdAndRelationTypeForRoom(roomId, characterId, relationType)
    }
}

