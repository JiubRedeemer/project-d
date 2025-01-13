package com.jiubredeemer.app.integration.rulebook

import com.jiubredeemer.app.integration.configuration.RuleBookProperty
import com.jiubredeemer.app.integration.rulebook.dto.ability.AbilityDto
import com.jiubredeemer.app.integration.rulebook.dto.clazz.ClazzDto
import com.jiubredeemer.app.integration.rulebook.dto.race.RaceDto
import com.jiubredeemer.app.integration.dto.request.RequestByRoomId
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto
import com.jiubredeemer.app.integration.rulebook.dto.skill.SkillByClassRequest
import com.jiubredeemer.app.integration.rulebook.dto.skill.SkillByCodeRequest
import com.jiubredeemer.app.integration.rulebook.dto.skill.SkillDto
import com.jiubredeemer.common.exception.IntegrationAccessException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.util.*

@Service
class RuleBookClient(
    private val restClient: RestClient,
    private val ruleBookProperty: RuleBookProperty,
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
            throw IntegrationAccessException("Rulebook dont response, cause: ${e.message}")
        }
    }

    fun getRacesForRoom(roomId: UUID): List<RaceDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.post()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.racesUrl)
            .headers { it.addAll(headers) }
            .body(RequestByRoomId(roomId))
            .retrieve()
            .toEntity(object : ParameterizedTypeReference<List<RaceDto>>() {})

        return response.body
    }

    fun getClassesForRoom(roomId: UUID): List<ClazzDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.post()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.classesUrl)
            .headers { it.addAll(headers) }
            .body(RequestByRoomId(roomId))
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<List<ClazzDto>>() {})

        return response.body
    }

    fun getAbilitiesForRoom(roomId: UUID): List<AbilityDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.post()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.abilitiesUrl)
            .headers { it.addAll(headers) }
            .body(RequestByRoomId(roomId))
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<List<AbilityDto>>() {})

        return response.body
    }

    fun getSkillsForRoom(roomId: UUID): List<SkillDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.post()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.skillsUrl)
            .headers { it.addAll(headers) }
            .body(RequestByRoomId(roomId))
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<List<SkillDto>>() {})

        return response.body
    }

    fun getSkillByCodeForRoom(roomId: UUID, code: String): SkillDto? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.post()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.skillsByCodeUrl)
            .headers { it.addAll(headers) }
            .body(SkillByCodeRequest(roomId, code))
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<SkillDto>() {})
        return response.body
    }

    fun getSkillsByClassForRoom(roomId: UUID, classCode: String): List<SkillDto>? {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val response = restClient.post()
            .uri(ruleBookProperty.baseUrl + ruleBookProperty.skillsByClassUrl)
            .headers { it.addAll(headers) }
            .body(SkillByClassRequest(roomId, classCode))
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<List<SkillDto>>() {})

        return response.body
    }

    fun deleteRoom(id: UUID) {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        try {
            restClient.delete()
                .uri(ruleBookProperty.baseUrl + ruleBookProperty.roomsUrl + id)
                .headers { it.addAll(headers) }
        } catch (e: Exception) {
            throw IntegrationAccessException("Rulebook dont response, cause: ${e.message}")
        }
    }
}
