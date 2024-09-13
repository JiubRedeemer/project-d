package com.jiubredeemer.app.integration

import com.jiubredeemer.app.integration.dto.clazz.ClassDto
import com.jiubredeemer.app.integration.dto.race.RaceDto
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*


@Service
class RuleBookClient(
    private val restTemplate: RestTemplate,
    private val ruleBookProperty: RuleBookProperty
) {

    fun persistRoom(roomCreateRequestDto: RoomCreateRequestDto?): RoomCreateRequestDto? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val requestEntity: HttpEntity<RoomCreateRequestDto> = HttpEntity(roomCreateRequestDto, headers)

        val responseEntity: ResponseEntity<RoomCreateRequestDto> = restTemplate.exchange(
            ruleBookProperty.baseUrl + ruleBookProperty.roomsUrl,
            HttpMethod.PUT,
            requestEntity,
            RoomCreateRequestDto::class.java
        )

        return responseEntity.body
    }

    fun getRacesForRoom(roomId: UUID): List<RaceDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val requestEntity: HttpEntity<UUID> = HttpEntity(roomId, headers)

        val responseEntity: ResponseEntity<List<RaceDto>> = restTemplate.exchange(
            ruleBookProperty.baseUrl + ruleBookProperty.racesUrl,
            HttpMethod.GET,
            requestEntity,
            ParameterizedTypeReference.forType(ArrayList<RaceDto>().javaClass)
        )

        return responseEntity.body
    }

    fun getClassesForRoom(roomId: UUID): List<ClassDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val requestEntity: HttpEntity<UUID> = HttpEntity(roomId, headers)

        val responseEntity: ResponseEntity<List<ClassDto>> = restTemplate.exchange(
            ruleBookProperty.baseUrl + ruleBookProperty.classesUrl,
            HttpMethod.GET,
            requestEntity,
            ParameterizedTypeReference.forType(ArrayList<ClassDto>().javaClass)
        )

        return responseEntity.body
    }
}
