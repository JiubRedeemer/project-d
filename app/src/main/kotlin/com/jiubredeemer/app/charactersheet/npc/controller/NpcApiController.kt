package com.jiubredeemer.app.charactersheet.npc.controller

import com.jiubredeemer.app.charactersheet.npc.dto.NpcDto
import com.jiubredeemer.app.charactersheet.npc.service.NpcApiService
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
}

