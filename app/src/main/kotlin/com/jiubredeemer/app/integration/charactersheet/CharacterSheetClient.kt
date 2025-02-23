package com.jiubredeemer.app.integration.charactersheet

import com.jiubredeemer.app.charactersheet.character.dto.CharacterDto
import com.jiubredeemer.app.charactersheet.character.dto.UpdateCurrentHealthRequest
import com.jiubredeemer.app.integration.charactersheet.dto.character.BonusValueUpdateRequest
import com.jiubredeemer.app.integration.charactersheet.dto.character.CreateCharacterRequest
import com.jiubredeemer.app.integration.charactersheet.dto.character.FindCharacterByUserIdAndRoomIdRequest
import com.jiubredeemer.app.integration.charactersheet.dto.character.UpdateMasteryRequest
import com.jiubredeemer.app.integration.configuration.CharacterSheetProperty
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto
import com.jiubredeemer.common.exception.IntegrationAccessException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class CharacterSheetClient(
    private val restClient: RestClient,
    private val characterSheetProperty: CharacterSheetProperty
) {
    private val headers = HttpHeaders().apply { set("Content-Type", "application/json") }

    fun persistRoom(roomCreateRequestDto: RoomCreateRequestDto): RoomCreateRequestDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.roomsUrl)
                .toUriString()
            val response = restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(roomCreateRequestDto)
                .retrieve()
                .toEntity(RoomCreateRequestDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on persistRoom, cause: ${e.message}")
        }
    }

    fun deleteRoom(id: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.roomsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on deleteRoom, cause: ${e.message}")
        }
    }

    fun createCharacter(roomId: UUID, createCharacterRequest: CreateCharacterRequest): CharacterDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .toUriString()
            val response = restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(createCharacterRequest)
                .retrieve()
                .toEntity(CharacterDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on createCharacter, cause: ${e.message}")
        }
    }

    fun findAllByRoomIdAndUserId(roomId: UUID, userId: UUID): List<CharacterDto>? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .toUriString()
            val response = restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(FindCharacterByUserIdAndRoomIdRequest(roomId, userId))
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<CharacterDto>>() {})
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on findAllByRoomIdAndUserId, cause: ${e.message}")
        }
    }

    fun findByCharacterId(characterId: UUID): CharacterDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .toUriString()
            val response = restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(CharacterDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on findByCharacterId, cause: ${e.message}")
        }
    }

    fun getHeaderInfoByCharacterId(characterId: UUID): CharacterDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.headerUrl)
                .toUriString()
            val response = restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(CharacterDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on getHeaderInfoByCharacterId, cause: ${e.message}")
        }
    }

    fun getSubheaderInfoByCharacterId(characterId: UUID): CharacterDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.subheaderUrl)
                .toUriString()
            val response = restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(CharacterDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on getSubheaderInfoByCharacterId, cause: ${e.message}")
        }
    }

    fun getAbilitiesAndSkillsInfoByCharacterId(characterId: UUID): CharacterDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.abilitiesUrl)
                .toUriString()
            val response = restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(CharacterDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on getAbilitiesAndSkillsInfoByCharacterId, cause: ${e.message}")
        }
    }

    fun updateAbilityBonusValue(
        characterId: UUID,
        abilityCode: String,
        bonusValueRequest: BonusValueUpdateRequest
    ) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.abilitiesUrl)
                .pathSegment(abilityCode)
                .pathSegment(characterSheetProperty.bonusUrl)
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(bonusValueRequest)
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on updateAbilityBonusValue, cause: ${e.message}")
        }
    }

    fun updateHealthBonusValue(
        characterId: UUID,
        bonusValueRequest: BonusValueUpdateRequest
    ) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.healthUrl)
                .pathSegment(characterSheetProperty.bonusUrl)
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(bonusValueRequest)
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on updateHealthBonusValue, cause: ${e.message}")
        }
    }

    fun updateArmoryClassBonusValue(
        characterId: UUID,
        bonusValueRequest: BonusValueUpdateRequest
    ) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.armoryClassUrl)
                .pathSegment(characterSheetProperty.bonusUrl)
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(bonusValueRequest)
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on updateArmoryClassBonusValue, cause: ${e.message}")
        }
    }

    fun updateSpeedBonusValue(
        characterId: UUID,
        bonusValueRequest: BonusValueUpdateRequest
    ) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.speedUrl)
                .pathSegment(characterSheetProperty.bonusUrl)
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(bonusValueRequest)
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on updateArmoryClassBonusValue, cause: ${e.message}")
        }
    }

    fun updateInitiativeBonusValue(
        characterId: UUID,
        bonusValueRequest: BonusValueUpdateRequest
    ) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.initiativeUrl)
                .pathSegment(characterSheetProperty.bonusUrl)
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(bonusValueRequest)
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on updateArmoryClassBonusValue, cause: ${e.message}")
        }
    }

    fun updateCurrentHealthById(characterId: UUID, updateCurrentHealthRequest: UpdateCurrentHealthRequest) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.healthUrl)
                .pathSegment(characterSheetProperty.updateCurrentHealthUrl)
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(updateCurrentHealthRequest)
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on updateCurrentHealthById, cause: ${e.message}")
        }
    }

    fun updateSkillMasteryByCode(characterId: UUID, code: String, request: UpdateMasteryRequest) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.skillsUrl)
                .pathSegment(code)
                .pathSegment(characterSheetProperty.updateMasteryUrl)
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(request)
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on updateMasteryByCode, cause: ${e.message}")
        }
    }
}
