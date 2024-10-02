package com.jiubredeemer.app.integration

import com.jiubredeemer.app.integration.configuration.RuleBookProperty
import com.jiubredeemer.app.integration.dto.ability.AbilityDto
import com.jiubredeemer.app.integration.dto.clazz.ClassDto
import com.jiubredeemer.app.integration.dto.race.RaceDto
import com.jiubredeemer.app.integration.dto.request.RequestByRoomId
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto
import com.jiubredeemer.common.exceptions.IntegrationAccessException
import com.jiubredeemer.dal.service.RoomService
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.util.*

@Service
class RuleBookClient(
    private val restClient: RestClient,
    private val ruleBookProperty: RuleBookProperty,
    private val roomService: RoomService
) {

    fun persistRoom(roomCreateRequestDto: RoomCreateRequestDto): RoomCreateRequestDto? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        try {
            val response = restClient.put()
                .uri(ruleBookProperty.baseUrl + ruleBookProperty.roomsUrl)
                .headers { it.addAll(headers) }
                .body(roomCreateRequestDto)
                .retrieve()
                .toEntity(RoomCreateRequestDto::class.java)
            return response.body
        } catch (e: Exception) {
            roomService.delete(roomId = roomCreateRequestDto.roomId) //  Если не получилось заперсистить в книге правил,
            // мы не даём пользователю сохранить комнату
            throw IntegrationAccessException("Rulebook dont response, cause: ${e.message}")
        }
    }

    fun getRacesForRoom(roomId: UUID): List<RaceDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.post()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.racesUrl)
            .headers { it.addAll(headers) }
            .body(RequestByRoomId(roomId))  // Передаем тело запроса
            .retrieve()
            .toEntity(object : ParameterizedTypeReference<List<RaceDto>>() {})  // Используем ParameterizedTypeReference

        return response.body
    }

    fun getClassesForRoom(roomId: UUID): List<ClassDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.post()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.classesUrl)
            .headers { it.addAll(headers) }
            .body(RequestByRoomId(roomId))  // Передаем тело запроса
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<List<ClassDto>>() {})  // Используем ParameterizedTypeReference

        return response.body
    }

    fun getAbilitiesForRoom(roomId: UUID): List<AbilityDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.post()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.abilitiesUrl)
            .headers { it.addAll(headers) }
            .body(RequestByRoomId(roomId))  // Передаем тело запроса
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<List<AbilityDto>>() {})  // Используем ParameterizedTypeReference

        return response.body
    }
}
