package com.jiubredeemer.app.abilities.service

import com.jiubredeemer.app.abilities.dto.AbilityFullDto
import com.jiubredeemer.app.integration.RuleBookClient
import com.jiubredeemer.app.integration.dto.ability.AbilityDto
import com.jiubredeemer.app.rooms.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import org.springframework.stereotype.Service
import java.util.*

@Service
class AbilityApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val ruleBookClient: RuleBookClient
) {
    fun getAbilities(roomId: UUID): List<AbilityFullDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val abilitiesForRoom: List<AbilityDto> = ruleBookClient.getAbilitiesForRoom(roomId) ?: return listOf()
        return abilitiesForRoom.map { AbilityFullDto(it.name, it.code, it.roomId) }
    }
}
