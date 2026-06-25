package com.jiubredeemer.app.charactersheet.npc.service

import com.jiubredeemer.app.charactersheet.npc.dto.NpcNpcRelationDto
import com.jiubredeemer.app.integration.charactersheet.CharacterSheetClient
import com.jiubredeemer.app.integration.charactersheet.dto.npc.SaveNpcNpcRelationRequest
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NpcNpcRelationApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val characterSheetClient: CharacterSheetClient,
) {
    fun save(roomId: UUID, request: SaveNpcNpcRelationRequest): NpcNpcRelationDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.saveNpcNpcRelation(request)
    }

    fun deleteById(roomId: UUID, id: UUID) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.deleteNpcNpcRelationById(id)
    }

    fun getByNpcId(roomId: UUID, npcId: UUID): List<NpcNpcRelationDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.getNpcNpcRelationsByNpcId(npcId) ?: emptyList()
    }

    fun getAllForRoom(roomId: UUID, npcIds: List<UUID>): List<NpcNpcRelationDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.getNpcNpcRelationsByNpcIds(npcIds) ?: emptyList()
    }
}
