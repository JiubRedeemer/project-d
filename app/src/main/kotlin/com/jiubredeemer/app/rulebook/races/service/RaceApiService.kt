package com.jiubredeemer.app.rulebook.races.service

import com.jiubredeemer.app.integration.RuleBookClient
import com.jiubredeemer.app.integration.dto.race.RaceDto
import com.jiubredeemer.app.rulebook.races.model.RaceCreateInfoDto
import com.jiubredeemer.app.rooms.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import org.springframework.stereotype.Service
import java.util.*

@Service
class RaceApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val ruleBookClient: RuleBookClient
) {
    fun getRaces(roomId: UUID): List<RaceCreateInfoDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val racesForRoom: List<RaceDto> = ruleBookClient.getRacesForRoom(roomId) ?: return listOf()
        return racesForRoom.map { RaceCreateInfoDto(it.name, it.description ?: "", it.code, it.stats) }
    }
}
