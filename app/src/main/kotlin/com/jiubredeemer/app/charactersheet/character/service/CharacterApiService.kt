package com.jiubredeemer.app.charactersheet.character.service

import com.jiubredeemer.app.charactersheet.character.dto.CharacterDto
import com.jiubredeemer.app.integration.charactersheet.CharacterSheetClient
import com.jiubredeemer.app.integration.charactersheet.dto.character.CreateCharacterRequest
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import org.springframework.stereotype.Service
import java.util.*

@Service
class CharacterApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val characterSheetClient: CharacterSheetClient,
) {
    fun createCharacter(roomId: UUID, createCharacterRequest: CreateCharacterRequest): CharacterDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        createCharacterRequest.userId = accessChecker.getCurrentUser().id!!
        val characterUUID: CharacterDto? = characterSheetClient.createCharacter(roomId, createCharacterRequest)
        return characterUUID
    }

    fun findAllByRoomIdAndUserId(roomId: UUID): List<CharacterDto>? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val userId = accessChecker.getCurrentUser().id!!
        val characters: List<CharacterDto>? = characterSheetClient.findAllByRoomIdAndUserId(roomId, userId)
        return characters
    }

    fun findByCharacterId(roomId: UUID, characterId: UUID): CharacterDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val character: CharacterDto? = characterSheetClient.findByCharacterId(characterId)
        return character
    }
}
