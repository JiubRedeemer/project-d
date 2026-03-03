package com.jiubredeemer.app.rulebook.race.service

import com.jiubredeemer.app.integration.rulebook.RuleBookClient
import com.jiubredeemer.app.integration.rulebook.dto.race.RaceGroupDto
import com.jiubredeemer.app.integration.rulebook.dto.race.RaceDto
import com.jiubredeemer.app.rulebook.race.model.RaceCreateInfoDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class RaceApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val ruleBookClient: RuleBookClient
) {
    fun getGroupedRaces(roomId: UUID): List<RaceGroupDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getGroupedRacesForRoom(roomId) ?: listOf()
    }

    fun getRaces(roomId: UUID): List<RaceCreateInfoDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val racesForRoom: List<RaceDto> = ruleBookClient.getRacesForRoom(roomId) ?: return listOf()
        return racesForRoom.map {
            RaceCreateInfoDto(it.name, it.description ?: "", it.code, it.speciesCode, it.imgUrl, it.stats)
        }
    }

    fun createRace(roomId: UUID, raceDto: RaceDto): RaceDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.createRace(raceDto) ?: throw NotFoundException("Failed to create race")
    }

    fun getRootRaces(roomId: UUID): List<RaceDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getRootRacesForRoom(roomId) ?: listOf()
    }

    fun getRaceByCode(roomId: UUID, code: String): RaceDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getRaceByCode(roomId, code)
            ?: throw NotFoundException("Race not found by code: $code")
    }

    fun getRaceSubspeciesByCode(roomId: UUID, code: String): List<RaceDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getRaceSubspeciesByCode(roomId, code) ?: listOf()
    }
}
