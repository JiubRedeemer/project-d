package com.jiubredeemer.app.charactersheet.character.service

import com.jiubredeemer.app.charactersheet.character.dto.CharacterBioUpdateRequest
import com.jiubredeemer.app.charactersheet.character.dto.CharacterDto
import com.jiubredeemer.app.charactersheet.character.dto.UpdateCurrentHealthRequest
import com.jiubredeemer.app.integration.charactersheet.CharacterSheetClient
import com.jiubredeemer.app.integration.charactersheet.dto.character.BonusValueUpdateRequest
import com.jiubredeemer.app.integration.charactersheet.dto.character.CreateCharacterRequest
import com.jiubredeemer.app.integration.charactersheet.dto.character.UpdateMasteryRequest
import com.jiubredeemer.app.itemstorage.inventory.service.InventoryApiService
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

    fun getHeaderInfoByCharacterId(roomId: UUID, characterId: UUID): CharacterDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val character: CharacterDto? = characterSheetClient.getHeaderInfoByCharacterId(characterId)
        return character
    }

    fun getSubheaderInfoByCharacterId(roomId: UUID, characterId: UUID): CharacterDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val character: CharacterDto? = characterSheetClient.getSubheaderInfoByCharacterId(characterId)
        return character
    }

    fun getAbilitiesAndSkillsInfoByCharacterId(roomId: UUID, characterId: UUID): CharacterDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val character: CharacterDto? = characterSheetClient.getAbilitiesAndSkillsInfoByCharacterId(characterId)
        return character
    }

    fun getBioByCharacterId(roomId: UUID, characterId: UUID): CharacterDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val character: CharacterDto? = characterSheetClient.getBioByCharacterId(characterId)
        return character
    }

    fun updateInitiativeBonusValue(roomId: UUID, characterId: UUID, bonusValueUpdateRequest: BonusValueUpdateRequest) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updateInitiativeBonusValue(characterId, bonusValueUpdateRequest)
    }

    fun updateSpeedBonusValue(roomId: UUID, characterId: UUID, bonusValueUpdateRequest: BonusValueUpdateRequest) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updateSpeedBonusValue(characterId, bonusValueUpdateRequest)
    }

    fun updateArmoryClassBonusValue(roomId: UUID, characterId: UUID, bonusValueUpdateRequest: BonusValueUpdateRequest) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updateArmoryClassBonusValue(characterId, bonusValueUpdateRequest)
    }

    fun updateHealthBonusValue(roomId: UUID, characterId: UUID, bonusValueUpdateRequest: BonusValueUpdateRequest) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updateHealthBonusValue(characterId, bonusValueUpdateRequest)
    }

    fun updateAbilityBonusValue(
        roomId: UUID,
        characterId: UUID,
        code: String,
        bonusValueUpdateRequest: BonusValueUpdateRequest
    ) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updateAbilityBonusValue(characterId, code, bonusValueUpdateRequest)
    }

    fun updateCurrentHealthById(
        roomId: UUID,
        characterId: UUID,
        updateCurrentHealthRequest: UpdateCurrentHealthRequest
    ) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updateCurrentHealthById(characterId, updateCurrentHealthRequest)
    }

    fun updateSkillMasteryByCode(
        roomId: UUID,
        characterId: UUID,
        code: String,
        request: UpdateMasteryRequest
    ) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updateSkillMasteryByCode(characterId, code, request)
    }

    fun updateBioByCharacterId(
        roomId: UUID,
        characterId: UUID,
        section: String,
        characterBioUpdateRequest: CharacterBioUpdateRequest
    ): CharacterDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.updateBioByCharacterId(characterId, section, characterBioUpdateRequest)
    }
}
