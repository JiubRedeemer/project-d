package com.jiubredeemer.app.integration.charactersheet

import com.jiubredeemer.app.charactersheet.character.dto.CharacterDto
import com.jiubredeemer.app.integration.charactersheet.dto.character.CreateCharacterRequest
import com.jiubredeemer.app.integration.charactersheet.dto.character.FindCharacterByUserIdAndRoomIdRequest
import com.jiubredeemer.app.integration.configuration.CharacterSheetProperty
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto
import com.jiubredeemer.common.exception.IntegrationAccessException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.util.*

@Service
class CharacterSheetClient(
    private val restClient: RestClient,
    private val characterSheetProperty: CharacterSheetProperty,
) {

    fun persistRoom(roomCreateRequestDto: RoomCreateRequestDto): RoomCreateRequestDto? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        try {
            val response = restClient.put()
                .uri(characterSheetProperty.baseUrl + characterSheetProperty.roomsUrl)
                .headers { it.addAll(headers) }
                .body(roomCreateRequestDto)
                .retrieve()
                .toEntity(RoomCreateRequestDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response, cause: ${e.message}")
        }
    }

    fun deleteRoom(id: UUID) {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        try {
            restClient.delete()
                .uri(characterSheetProperty.baseUrl + characterSheetProperty.roomsUrl + id)
                .headers { it.addAll(headers) }
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response, cause: ${e.message}")
        }
    }

    fun createCharacter(roomId: UUID, createCharacterRequest: CreateCharacterRequest): CharacterDto? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        try {
            val response = restClient.put()
                .uri(characterSheetProperty.baseUrl + characterSheetProperty.charactersUrl)
                .headers { it.addAll(headers) }
                .body(createCharacterRequest)
                .retrieve()
                .toEntity(CharacterDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response, cause: ${e.message}")
        }
    }

    fun findAllByRoomIdAndUserId(roomId: UUID, userId: UUID): List<CharacterDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        try {
            val response = restClient.post()
                .uri(characterSheetProperty.baseUrl + characterSheetProperty.charactersUrl)
                .headers { it.addAll(headers) }
                .body(FindCharacterByUserIdAndRoomIdRequest(roomId, userId))
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<CharacterDto>>() {})
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response, cause: ${e.message}")
        }
    }

    fun findByCharacterId(characterId: UUID): CharacterDto? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        try {
            val response = restClient.post()
                .uri(characterSheetProperty.baseUrl + characterSheetProperty.charactersUrl + "/" + characterId)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(CharacterDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("CharacterSheet don't response, cause: ${e.message}")
        }
    }
}
