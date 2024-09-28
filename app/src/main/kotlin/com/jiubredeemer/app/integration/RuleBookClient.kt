package com.jiubredeemer.app.integration

import com.jiubredeemer.app.integration.configuration.RuleBookProperty
import com.jiubredeemer.app.integration.dto.clazz.ClassDto
import com.jiubredeemer.app.integration.dto.race.RaceDto
import com.jiubredeemer.app.integration.dto.race.request.RacesRequest
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.util.*

@Service
class RuleBookClient(
    private val restClient: RestClient,
    private val ruleBookProperty: RuleBookProperty
) {

    fun persistRoom(roomCreateRequestDto: RoomCreateRequestDto): RoomCreateRequestDto? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.put()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.roomsUrl)
            .headers { it.addAll(headers) }
            .body(roomCreateRequestDto)  // Передача тела запроса
            .retrieve()
            .toEntity(RoomCreateRequestDto::class.java)  // Получаем ответ в виде RoomCreateRequestDto

        return if (response.statusCode == HttpStatusCode.valueOf(200)) response.body else null
    }

    fun getRacesForRoom(roomId: UUID): List<RaceDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.post()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.racesUrl)
            .headers { it.addAll(headers) }
            .body(RacesRequest(roomId))  // Передаем тело запроса
            .retrieve()
            .toEntity(object : ParameterizedTypeReference<List<RaceDto>>() {})  // Используем ParameterizedTypeReference

        return response.body
    }

    fun getClassesForRoom(roomId: UUID): List<ClassDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.get()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.classesUrl)
            .headers { it.addAll(headers) }
            .retrieve()
            .toEntity(object : ParameterizedTypeReference<List<ClassDto>>() {})  // Используем ParameterizedTypeReference

        return response.body
    }
}
