package com.jiubredeemer.app.charactersheet.npc.service

import com.jiubredeemer.app.charactersheet.npc.dto.CharacterNpcRelationDto
import com.jiubredeemer.app.charactersheet.npc.dto.NpcDto
import com.jiubredeemer.app.charactersheet.npc.dto.RelationTypeEnum
import com.jiubredeemer.app.integration.charactersheet.CharacterSheetClient
import com.jiubredeemer.app.integration.charactersheet.dto.npc.SaveCharacterNpcRelationRequest
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import org.springframework.stereotype.Service
import java.util.*

@Service
class CharacterNpcRelationApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val characterSheetClient: CharacterSheetClient,
) {
    fun saveCharacterNpcRelationForRoom(
        roomId: UUID,
        characterId: UUID,
        request: SaveCharacterNpcRelationRequest,
    ): CharacterNpcRelationDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        request.characterId = characterId
        return characterSheetClient.saveCharacterNpcRelation(request)
    }

    fun deleteCharacterNpcRelationByIdForRoom(
        roomId: UUID,
        characterId: UUID,
        id: UUID,
    ) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.deleteCharacterNpcRelationById(id)
    }

    fun getNpcsByCharacterIdForRoom(roomId: UUID, characterId: UUID): List<NpcDto>? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.getNpcsByCharacterId(characterId)
    }

    fun getNpcsByCharacterIdAndRelationTypeForRoom(
        roomId: UUID,
        characterId: UUID,
        relationType: RelationTypeEnum,
    ): List<NpcDto>? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.getNpcsByCharacterIdAndRelationType(characterId, relationType.name)
    }
}

