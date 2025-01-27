package com.jiubredeemer.app.integration.rulebook

import com.jiubredeemer.app.integration.configuration.RuleBookProperty
import com.jiubredeemer.app.integration.dto.request.RequestByRoomId
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto
import com.jiubredeemer.app.integration.rulebook.dto.ability.AbilityDto
import com.jiubredeemer.app.integration.rulebook.dto.clazz.ClazzDto
import com.jiubredeemer.app.integration.rulebook.dto.race.RaceDto
import com.jiubredeemer.app.integration.rulebook.dto.skill.SkillByClassRequest
import com.jiubredeemer.app.integration.rulebook.dto.skill.SkillByCodeRequest
import com.jiubredeemer.app.integration.rulebook.dto.skill.SkillDto
import com.jiubredeemer.common.exception.IntegrationAccessException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class RuleBookClient(
    private val restClient: RestClient,
    private val ruleBookProperty: RuleBookProperty,
) {
    private val headers = HttpHeaders().apply { set("Content-Type", "application/json") }

    fun persistRoom(roomCreateRequestDto: RoomCreateRequestDto): RoomCreateRequestDto? {
        val uri = UriComponentsBuilder
            .fromHttpUrl(ruleBookProperty.baseUrl)
            .pathSegment(ruleBookProperty.apiUrl)
            .pathSegment(ruleBookProperty.roomsUrl)
            .toUriString()

        try {
            val response = restClient.put()
                .uri(uri)
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
        val uri = UriComponentsBuilder
            .fromHttpUrl(ruleBookProperty.baseUrl)
            .pathSegment(ruleBookProperty.apiUrl)
            .pathSegment(ruleBookProperty.racesUrl)
            .toUriString()

        val response = restClient.post()
            .uri(uri)
            .headers { it.addAll(headers) }
            .body(RequestByRoomId(roomId))
            .retrieve()
            .toEntity(object : ParameterizedTypeReference<List<RaceDto>>() {})

        return response.body
    }

    fun getClassesForRoom(roomId: UUID): List<ClazzDto>? {
        val uri = UriComponentsBuilder
            .fromHttpUrl(ruleBookProperty.baseUrl)
            .pathSegment(ruleBookProperty.apiUrl)
            .pathSegment(ruleBookProperty.classesUrl)
            .toUriString()

        val response = restClient.post()
            .uri(uri)
            .headers { it.addAll(headers) }
            .body(RequestByRoomId(roomId))
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<List<ClazzDto>>() {})

        return response.body
    }

    fun getAbilitiesForRoom(roomId: UUID): List<AbilityDto>? {
        val uri = UriComponentsBuilder
            .fromHttpUrl(ruleBookProperty.baseUrl)
            .pathSegment(ruleBookProperty.apiUrl)
            .pathSegment(ruleBookProperty.abilitiesUrl)
            .toUriString()

        val response = restClient.post()
            .uri(uri)
            .headers { it.addAll(headers) }
            .body(RequestByRoomId(roomId))
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<List<AbilityDto>>() {})

        return response.body
    }

    fun getSkillsForRoom(roomId: UUID): List<SkillDto>? {
        val uri = UriComponentsBuilder
            .fromHttpUrl(ruleBookProperty.baseUrl)
            .pathSegment(ruleBookProperty.apiUrl)
            .pathSegment(ruleBookProperty.skillsUrl)
            .toUriString()

        val response = restClient.post()
            .uri(uri)
            .headers { it.addAll(headers) }
            .body(RequestByRoomId(roomId))
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<List<SkillDto>>() {})

        return response.body
    }

    fun getSkillByCodeForRoom(roomId: UUID, code: String): SkillDto? {
        val uri = UriComponentsBuilder
            .fromHttpUrl(ruleBookProperty.baseUrl)
            .pathSegment(ruleBookProperty.apiUrl)
            .pathSegment(ruleBookProperty.skillsUrl)
            .pathSegment(ruleBookProperty.skillsByCodeUrl)
            .toUriString()

        val response = restClient.post()
            .uri(uri)
            .headers { it.addAll(headers) }
            .body(SkillByCodeRequest(roomId, code))
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<SkillDto>() {})
        return response.body
    }

    fun getSkillsByClassForRoom(roomId: UUID, classCode: String): List<SkillDto>? {
        val uri = UriComponentsBuilder
            .fromHttpUrl(ruleBookProperty.baseUrl)
            .pathSegment(ruleBookProperty.apiUrl)
            .pathSegment(ruleBookProperty.skillsUrl)
            .pathSegment(ruleBookProperty.skillsByClassUrl)
            .toUriString()

        val response = restClient.post()
            .uri(uri)
            .headers { it.addAll(headers) }
            .body(SkillByClassRequest(roomId, classCode))
            .retrieve()
            .toEntity(object :
                ParameterizedTypeReference<List<SkillDto>>() {})

        return response.body
    }

    fun deleteRoom(id: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(ruleBookProperty.baseUrl)
                .pathSegment(ruleBookProperty.apiUrl)
                .pathSegment(ruleBookProperty.roomsUrl)
                .pathSegment(id.toString())
                .toUriString()

            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
        } catch (e: Exception) {
            throw IntegrationAccessException("Rulebook dont response, cause: ${e.message}")
        }
    }
}
