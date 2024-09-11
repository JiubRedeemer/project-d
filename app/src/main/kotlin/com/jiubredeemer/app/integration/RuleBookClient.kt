package com.jiubredeemer.app.integration

import com.jiubredeemer.app.integration.dto.RoomDto
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class RuleBookClient(
    private val restTemplate: RestTemplate,
    private val ruleBookProperty: RuleBookProperty
) {

    fun saveRoom(roomDto: RoomDto?): RoomDto? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val requestEntity: HttpEntity<RoomDto> = HttpEntity(roomDto, headers)

        val responseEntity: ResponseEntity<RoomDto> = restTemplate.exchange(
            ruleBookProperty.baseUrl + ruleBookProperty.roomsUrl,
            HttpMethod.POST,
            requestEntity,
            RoomDto::class.java
        )

        return responseEntity.body
    }
}
