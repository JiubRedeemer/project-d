package com.jiubredeemer.app.charactersheet.npc.service

import com.jiubredeemer.app.charactersheet.npc.dto.NpcDto
import com.jiubredeemer.app.integration.charactersheet.CharacterSheetClient
import com.jiubredeemer.app.integration.charactersheet.dto.npc.SaveNpcRequest
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import org.springframework.stereotype.Service
import java.util.*

@Service
class NpcApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val characterSheetClient: CharacterSheetClient,
) {
    fun saveNpcForRoom(roomId: UUID, request: SaveNpcRequest): NpcDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        request.roomId = roomId
        request.createdBy = accessChecker.getCurrentUser().id!!
        return characterSheetClient.saveNpc(request)
    }

    fun getNpcsByRoomIdForRoom(roomId: UUID, characterId: UUID?, forceAll: Boolean): List<NpcDto>? {
        val userId = accessChecker.getCurrentUser().id!!
        roomAccessChecker.hasAccessOrThrow(roomId, userId)
        return characterSheetClient.getNpcsByRoomId(roomId, userId, characterId, forceAll)
    }

    fun getNpcByIdForRoom(roomId: UUID, id: UUID): NpcDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.getNpcById(id)
    }

    fun deleteNpcByIdForRoom(roomId: UUID, id: UUID) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.deleteNpcById(id)
    }
}

