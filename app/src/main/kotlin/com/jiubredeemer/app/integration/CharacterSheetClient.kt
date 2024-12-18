package com.jiubredeemer.app.integration

import com.jiubredeemer.app.integration.configuration.CharacterSheetProperty
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto
import com.jiubredeemer.common.exceptions.IntegrationAccessException
import com.jiubredeemer.dal.service.RoomService
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.util.*

@Service
class CharacterSheetClient(
    private val restClient: RestClient,
    private val characterSheetProperty: CharacterSheetProperty,
    private val roomService: RoomService
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
            roomService.delete(roomId = roomCreateRequestDto.roomId) //  Если не получилось заперсистить в книге правил,
            // мы не даём пользователю сохранить комнату
            throw IntegrationAccessException("CharacterSheet dont response, cause: ${e.message}")
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
            throw IntegrationAccessException("CharacterSheet dont response, cause: ${e.message}")
        }
    }
}
