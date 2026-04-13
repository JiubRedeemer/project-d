package com.jiubredeemer.app.charactersheet.pet.service

import com.jiubredeemer.app.charactersheet.pet.dto.PetDto
import com.jiubredeemer.app.charactersheet.pet.dto.PetSkillDto
import com.jiubredeemer.app.integration.charactersheet.CharacterSheetClient
import com.jiubredeemer.app.integration.charactersheet.dto.character.BonusValueUpdateRequest
import com.jiubredeemer.app.integration.charactersheet.dto.pet.CreatePetRequest
import com.jiubredeemer.app.integration.charactersheet.dto.pet.PetHealthCurrentUpdateRequest
import com.jiubredeemer.app.integration.charactersheet.dto.pet.PetProfileUpdateRequest
import com.jiubredeemer.app.integration.charactersheet.dto.pet.PetSkillRequest
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import org.springframework.stereotype.Service
import java.util.*

@Service
class PetApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val characterSheetClient: CharacterSheetClient,
) {
    fun createPetForRoom(roomId: UUID, characterId: UUID, request: CreatePetRequest): PetDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.createPet(characterId, request)
    }

    fun getPetsByCharacterIdForRoom(roomId: UUID, characterId: UUID): List<PetDto>? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.getPetsByCharacterId(characterId)
    }

    fun getPetByIdForRoom(roomId: UUID, petId: UUID): PetDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.getPetById(petId)
    }

    fun deletePetLogicalByIdForRoom(roomId: UUID, petId: UUID) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.deletePetLogicalById(petId)
    }

    fun updatePetProfileForRoom(roomId: UUID, petId: UUID, request: PetProfileUpdateRequest) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updatePetProfile(petId, request)
    }

    fun updatePetAbilityBonusForRoom(roomId: UUID, petId: UUID, abilityCode: String, request: BonusValueUpdateRequest) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updatePetAbilityBonus(petId, abilityCode, request)
    }

    fun updatePetCurrentHpForRoom(roomId: UUID, petId: UUID, request: PetHealthCurrentUpdateRequest) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updatePetCurrentHp(petId, request)
    }

    fun updatePetMaxHpForRoom(roomId: UUID, petId: UUID, request: BonusValueUpdateRequest) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.updatePetMaxHp(petId, request)
    }

    fun createPetSkillForRoom(roomId: UUID, petId: UUID, request: PetSkillRequest): PetSkillDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.createPetSkill(petId, request)
    }

    fun updatePetSkillForRoom(roomId: UUID, petId: UUID, skillId: UUID, request: PetSkillRequest): PetSkillDto? {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return characterSheetClient.updatePetSkill(petId, skillId, request)
    }

    fun deletePetSkillForRoom(roomId: UUID, petId: UUID, skillId: UUID) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        characterSheetClient.deletePetSkill(petId, skillId)
    }
}
