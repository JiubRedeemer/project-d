package com.jiubredeemer.app.integration.charactersheet

import com.jiubredeemer.app.charactersheet.character.dto.*
import com.jiubredeemer.app.charactersheet.npc.dto.CharacterNpcRelationDto
import com.jiubredeemer.app.charactersheet.npc.dto.NpcDto
import com.jiubredeemer.app.integration.charactersheet.dto.character.*
import com.jiubredeemer.app.integration.charactersheet.dto.npc.SaveCharacterNpcRelationRequest
import com.jiubredeemer.app.integration.charactersheet.dto.npc.SaveNpcRequest
import com.jiubredeemer.app.integration.configuration.CharacterSheetProperty
import com.jiubredeemer.app.integration.dto.RestTypeEnum
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto
import com.jiubredeemer.common.exception.IntegrationAccessException
import com.jiubredeemer.dal.entity.RoomUser
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class CharacterSheetClient(
    private val restClient: RestClient,
    private val characterSheetProperty: CharacterSheetProperty
) {
    private val headers = HttpHeaders().apply { set("Content-Type", "application/json") }

    fun saveNpc(request: SaveNpcRequest): NpcDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.npcsUrl)
                .toUriString()
            return restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(request)
                .retrieve()
                .toEntity(NpcDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on saveNpc, cause: ${e.message}")
        }
    }

    fun getNpcById(id: UUID): NpcDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.npcsUrl)
                .pathSegment(id.toString())
                .toUriString()
            return restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(NpcDto::class.java)
                .body
        } catch (_: HttpClientErrorException.NotFound) {
            return null
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on getNpcById, cause: ${e.message}")
        }
    }

    fun deleteNpcById(id: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.npcsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on deleteNpcById, cause: ${e.message}")
        }
    }

    fun getNpcsByRoomId(roomId: UUID, userId: UUID, characterId: UUID?, forceAll: Boolean): List<NpcDto>? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.npcsUrl)
                .pathSegment("room")
                .pathSegment(roomId.toString())
                .queryParam("userId", userId.toString())
                .queryParam("characterId", characterId.toString())
                .queryParam("forceAll", forceAll.toString())
                .toUriString()
            return restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<NpcDto>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on getNpcsByRoomId, cause: ${e.message}")
        }
    }

    fun saveCharacterNpcRelation(request: SaveCharacterNpcRelationRequest): CharacterNpcRelationDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.characterNpcRelationsUrl)
                .toUriString()
            return restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(request)
                .retrieve()
                .toEntity(CharacterNpcRelationDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on saveCharacterNpcRelation, cause: ${e.message}")
        }
    }

    fun deleteCharacterNpcRelationById(id: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.characterNpcRelationsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException(
                "CharacterSheet don't response on deleteCharacterNpcRelationById, cause: ${e.message}"
            )
        }
    }

    fun getNpcsByCharacterId(characterId: UUID): List<NpcDto>? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.characterNpcRelationsUrl)
                .pathSegment("character")
                .pathSegment(characterId.toString())
                .toUriString()
            return restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<NpcDto>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on getNpcsByCharacterId, cause: ${e.message}")
        }
    }

    fun getNpcsByCharacterIdAndRelationType(characterId: UUID, relationType: String): List<NpcDto>? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.characterNpcRelationsUrl)
                .pathSegment("character")
                .pathSegment(characterId.toString())
                .pathSegment("relationType")
                .pathSegment(relationType)
                .toUriString()
            return restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<NpcDto>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException(
                "CharacterSheet don't response on getNpcsByCharacterIdAndRelationType, cause: ${e.message}"
            )
        }
    }

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

    fun logicDeleteById(id: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.roomsUrl)
                .pathSegment(id.toString())
                .pathSegment(characterSheetProperty.logicalUrl)
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }.retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on logicDeleteById, cause: ${e.message}")
        }
    }

    fun createCharacter(createCharacterRequest: CreateCharacterRequest): CharacterDto? {
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

    fun findAllByRoomIdAndUserId(roomId: UUID, userId: UUID, roles: List<RoomUser.Role>?): List<CharacterDto>? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .toUriString()
            val response = restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(FindCharacterByUserIdAndRoomIdRequest(roomId, userId, roles))
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

    fun getBioByCharacterId(characterId: UUID): CharacterDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.bioUrl)
                .toUriString()
            val response = restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(CharacterDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on getPersonalityByCharacterId, cause: ${e.message}")
        }
    }

    fun updateBioByCharacterId(
        characterId: UUID,
        section: String,
        characterBioUpdateRequest: CharacterBioUpdateRequest
    ): CharacterDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.bioUrl)
                .pathSegment(section)
                .toUriString()
            val response = restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(characterBioUpdateRequest)
                .retrieve()
                .toEntity(CharacterDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on updateBioByCharacterId, cause: ${e.message}")
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

    fun updateHealthMaxValue(
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
                .pathSegment(characterSheetProperty.healthMaxUrl)
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(bonusValueRequest)
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on updateHealthMaxValue, cause: ${e.message}")
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

    fun updateCurrentXpById(characterId: UUID, updateCurrentXpRequest: UpdateCurrentXpRequest) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.levelUrl)
                .pathSegment(characterSheetProperty.updateCurrentXpUrl)
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(updateCurrentXpRequest)
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on updateCurrentXpById, cause: ${e.message}")
        }
    }

    fun levelUp(characterId: UUID, force: Boolean = false) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.levelUrl)
                .pathSegment(characterSheetProperty.levelUpUrl)
                .queryParam("force", force)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on levelUp, cause: ${e.message}")
        }
    }

    fun levelDown(characterId: UUID, force: Boolean = false) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.levelUrl)
                .pathSegment(characterSheetProperty.levelDownUrl)
                .queryParam("force", force)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on levelDown, cause: ${e.message}")
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

    fun getCharacterSkills(characterId: UUID): List<CharacterSkillsDto>? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.characterSkillsUrl)
                .toUriString()
            return restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<CharacterSkillsDto>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on getCharacterSkills, cause: ${e.message}")
        }
    }

    fun saveCharacterSkill(characterId: UUID, characterSkillsDto: CharacterSkillsDto): CharacterSkillsDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.characterSkillsUrl)
                .toUriString()
            return restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(characterSkillsDto)
                .retrieve()
                .toEntity(CharacterSkillsDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on saveCharacterSkill, cause: ${e.message}")
        }
    }

    fun deleteCharacterSkill(characterId: UUID, characterSkillId: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.characterSkillsUrl)
                .pathSegment(characterSkillId.toString())
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on deleteCharacterSkill, cause: ${e.message}")
        }
    }

    fun updateCharacterSkill(
        characterId: UUID,
        characterSkillId: UUID,
        characterSkillsDto: CharacterSkillsDto
    ): CharacterSkillsDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.characterSkillsUrl)
                .pathSegment(characterSkillId.toString())
                .toUriString()
            return restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(characterSkillsDto)
                .retrieve()
                .toEntity(CharacterSkillsDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on updateCharacterSkill, cause: ${e.message}")
        }
    }

    fun useCharacterSkill(
        characterId: UUID,
        characterSkillId: UUID
    ): CharacterSkillsDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.characterSkillsUrl)
                .pathSegment(characterSkillId.toString())
                .pathSegment(characterSheetProperty.useUrl)
                .toUriString()
            return restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(CharacterSkillsDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on useCharacterSkill, cause: ${e.message}")
        }
    }

    fun characterRest(characterId: UUID, restType: RestTypeEnum, hpDiceCount: Int) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.restUrl)
                .pathSegment(restType.name)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(RestRequest(hpDiceCount))
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on characterRest, cause: ${e.message}")
        }
    }

    fun characterAddTrait(characterId: UUID, traits: CharacterDto.CharacterTraitsDto) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.traitsUrl)
                .toUriString()
            restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(traits)
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on characterAddTrait, cause: ${e.message}")
        }
    }

    fun characterDeleteTrait(characterId: UUID, traitId: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(characterSheetProperty.baseUrl)
                .pathSegment(characterSheetProperty.apiUrl)
                .pathSegment(characterSheetProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(characterSheetProperty.traitsUrl)
                .pathSegment(traitId.toString())
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response on characterDeleteTrait, cause: ${e.message}")
        }
    }


}
