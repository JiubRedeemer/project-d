package com.jiubredeemer.app.rulebook.race.service

import com.jiubredeemer.app.integration.rulebook.RuleBookClient
import com.jiubredeemer.app.integration.rulebook.dto.race.RaceGroupDto
import com.jiubredeemer.app.integration.rulebook.dto.race.RaceDto
import com.jiubredeemer.app.rulebook.race.model.RaceCreateInfoDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
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
        val racesForRoom: List<RaceDto> = ruleBookClient.getGroupedRacesForRoom(roomId)
            ?.flatMap { raceGroup -> listOfNotNull(raceGroup.species) + raceGroup.subspecies }
            ?: return listOf()
        return racesForRoom.map { RaceCreateInfoDto(it.name, it.description ?: "", it.code, it.imgUrl, it.stats) }
    }
}
