package com.jiubredeemer.app.abilities.service

import com.jiubredeemer.app.abilities.dto.AbilityResponse
import com.jiubredeemer.app.abilities.mapper.AbilityMapper
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
    private val ruleBookClient: RuleBookClient,
    private val abilityMapper: AbilityMapper
) {
    fun getAbilities(roomId: UUID): List<AbilityResponse> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val abilitiesForRoom: List<AbilityDto> = ruleBookClient.getAbilitiesForRoom(roomId) ?: return listOf()
        return abilityMapper.mapToResponse(abilitiesForRoom)
    }


}
