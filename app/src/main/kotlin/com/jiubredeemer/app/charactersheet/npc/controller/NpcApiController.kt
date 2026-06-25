package com.jiubredeemer.app.charactersheet.npc.controller

import com.jiubredeemer.app.charactersheet.npc.dto.CharacterNpcRelationDto
import com.jiubredeemer.app.charactersheet.npc.dto.NpcDto
import com.jiubredeemer.app.charactersheet.npc.dto.NpcNpcRelationDto
import com.jiubredeemer.app.charactersheet.npc.service.CharacterNpcRelationApiService
import com.jiubredeemer.app.charactersheet.npc.service.NpcApiService
import com.jiubredeemer.app.charactersheet.npc.service.NpcNpcRelationApiService
import com.jiubredeemer.app.integration.charactersheet.dto.npc.SaveNpcNpcRelationRequest
import com.jiubredeemer.app.integration.charactersheet.dto.npc.SaveNpcRequest
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/rooms/{roomId}/npcs")
@Tag(name = "NPCs", description = "API for NPCs (room scoped)")
class NpcApiController(
    private val npcApiService: NpcApiService,
    private val characterNpcRelationApiService: CharacterNpcRelationApiService,
    private val npcNpcRelationApiService: NpcNpcRelationApiService,
) {
    @PutMapping("")
    @HasRoleOrThrow("ADMIN", "USER")
    fun saveNpcForRoom(
        @PathVariable roomId: UUID,
        @RequestBody request: SaveNpcRequest,
    ): NpcDto? {
        return npcApiService.saveNpcForRoom(roomId, request)
    }

    @GetMapping("")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getNpcsByRoomIdForRoom(
        @PathVariable roomId: UUID,
        @RequestParam(required = false) characterId: UUID?,
        @RequestParam(required = false) forceAll: Boolean = false,
    ): List<NpcDto>? {
        return npcApiService.getNpcsByRoomIdForRoom(roomId, characterId, forceAll)
    }

    @GetMapping("/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getNpcByIdForRoom(
        @PathVariable roomId: UUID,
        @PathVariable id: UUID,
    ): NpcDto {
        return npcApiService.getNpcByIdForRoom(roomId, id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "NPC not found")
    }

    @DeleteMapping("/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun deleteNpcByIdForRoom(
        @PathVariable roomId: UUID,
        @PathVariable id: UUID,
    ) {
        npcApiService.deleteNpcByIdForRoom(roomId, id)
    }

    @GetMapping("/{id}/relations")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getRelationsByNpcId(
        @PathVariable roomId: UUID,
        @PathVariable id: UUID,
    ): List<CharacterNpcRelationDto> {
        return characterNpcRelationApiService.getRelationsByNpcIdForRoom(roomId, id)
    }

    @GetMapping("/relations")
    @HasRoleOrThrow("ADMIN")
    fun getAllRelationsForRoom(
        @PathVariable roomId: UUID,
    ): List<CharacterNpcRelationDto> {
        return characterNpcRelationApiService.getAllRelationsForRoom(roomId)
    }

    @GetMapping("/{id}/npc-relations")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getNpcNpcRelationsByNpcId(
        @PathVariable roomId: UUID,
        @PathVariable id: UUID,
    ): List<NpcNpcRelationDto> {
        return npcNpcRelationApiService.getByNpcId(roomId, id)
    }

    @PutMapping("/npc-relations")
    @HasRoleOrThrow("ADMIN", "USER")
    fun saveNpcNpcRelation(
        @PathVariable roomId: UUID,
        @RequestBody request: SaveNpcNpcRelationRequest,
    ): NpcNpcRelationDto? {
        return npcNpcRelationApiService.save(roomId, request)
    }

    @DeleteMapping("/npc-relations/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun deleteNpcNpcRelation(
        @PathVariable roomId: UUID,
        @PathVariable id: UUID,
    ) {
        npcNpcRelationApiService.deleteById(roomId, id)
    }

    @PostMapping("/npc-relations/by-npc-ids")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getAllNpcNpcRelationsForRoom(
        @PathVariable roomId: UUID,
        @RequestBody npcIds: List<UUID>,
    ): List<NpcNpcRelationDto> {
        return npcNpcRelationApiService.getAllForRoom(roomId, npcIds)
    }
}

