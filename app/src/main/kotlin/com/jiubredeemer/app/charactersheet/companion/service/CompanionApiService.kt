package com.jiubredeemer.app.charactersheet.companion.service

import com.jiubredeemer.app.charactersheet.companion.dto.CompanionDto
import com.jiubredeemer.app.integration.charactersheet.CharacterSheetClient
import com.jiubredeemer.app.integration.charactersheet.dto.companion.SaveCompanionRequest
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import org.springframework.stereotype.Service
import java.util.*

@Service
class CompanionApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val characterSheetClient: CharacterSheetClient,
) {
    fun saveCompanion(roomId: UUID, characterId: UUID, request: SaveCompanionRequest): CompanionDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        request.characterId = characterId
        return characterSheetClient.saveCompanion(request)
    }

    fun getCompanionsByCharacterId(roomId: UUID, characterId: UUID): List<CompanionDto>? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.getCompanionsByCharacterId(characterId)
    }

    fun getCompanionById(roomId: UUID, id: UUID): CompanionDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.getCompanionById(id)
    }

    fun updateCurrentHp(roomId: UUID, id: UUID, value: Int) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updateCompanionCurrentHp(id, value)
    }

    fun restoreFullHp(roomId: UUID, id: UUID) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.restoreCompanionFullHp(id)
    }

    fun deleteCompanion(roomId: UUID, id: UUID) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.deleteCompanionById(id)
    }
}
