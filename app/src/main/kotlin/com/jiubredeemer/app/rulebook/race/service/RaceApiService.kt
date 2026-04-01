package com.jiubredeemer.app.rulebook.race.service

import com.jiubredeemer.app.integration.dto.RuleTypeEnum
import com.jiubredeemer.app.integration.rulebook.RuleBookClient
import com.jiubredeemer.app.integration.rulebook.dto.race.RaceDto
import com.jiubredeemer.app.integration.rulebook.dto.race.RaceGroupDto
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
    fun getGroupedRaces(roomId: UUID, forceRuleTypeEnum: RuleTypeEnum?): List<RaceGroupDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getGroupedRacesForRoom(roomId, forceRuleTypeEnum) ?: listOf()
    }

    fun getRaces(roomId: UUID, forceRuleTypeEnum: RuleTypeEnum?): List<RaceDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val racesForRoom: List<RaceDto> = ruleBookClient.getRacesForRoom(roomId, forceRuleTypeEnum) ?: return listOf()
        return racesForRoom
    }

    fun createRace(raceDto: RaceDto): RaceDto {
        roomAccessChecker.hasAccessOrThrow(raceDto.roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.createRace(raceDto) ?: throw NotFoundException("Failed to create race")
    }

    fun updateRace(raceDto: RaceDto): RaceDto {
        roomAccessChecker.hasAccessOrThrow(raceDto.roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.updateRace(raceDto) ?: throw NotFoundException("Failed to update race")
    }

    fun setRaceHidden(id: UUID, hidden: Boolean): RaceDto {
        return ruleBookClient.setRaceHidden(id, hidden) ?: throw NotFoundException("Race not found by id: $id")
    }

    fun getRootRaces(roomId: UUID, forceRuleTypeEnum: RuleTypeEnum?): List<RaceDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getRootRacesForRoom(roomId, forceRuleTypeEnum) ?: listOf()
    }

    fun getRaceByCode(roomId: UUID, code: String): RaceDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getRaceByCode(roomId, code, null)
            ?: throw NotFoundException("Race not found by code: $code")
    }

    fun getRaceSubspeciesByCode(roomId: UUID, code: String, forceRuleTypeEnum: RuleTypeEnum?): List<RaceDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getRaceSubspeciesByCode(roomId, code, forceRuleTypeEnum) ?: listOf()
    }
}
