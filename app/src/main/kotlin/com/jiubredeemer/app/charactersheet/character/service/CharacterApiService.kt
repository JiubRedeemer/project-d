package com.jiubredeemer.app.charactersheet.character.service

import com.jiubredeemer.app.charactersheet.character.dto.CreateCharacterRequest
import com.jiubredeemer.app.integration.CharacterSheetClient
import com.jiubredeemer.app.rooms.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import org.springframework.stereotype.Service
import java.util.*

@Service
class CharacterApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val characterSheetClient: CharacterSheetClient,
) {
    fun createCharacter(roomId: UUID, createCharacterRequest: CreateCharacterRequest): UUID? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val characterUUID: UUID? = characterSheetClient.createCharacter(roomId, createCharacterRequest)
        return characterUUID
    }


}
