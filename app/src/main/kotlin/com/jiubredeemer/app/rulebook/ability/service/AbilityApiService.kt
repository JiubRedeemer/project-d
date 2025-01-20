package com.jiubredeemer.app.rulebook.ability.service

import com.jiubredeemer.app.integration.rulebook.RuleBookClient
import com.jiubredeemer.app.integration.rulebook.dto.ability.AbilityDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import org.springframework.stereotype.Service
import java.util.*

@Service
class AbilityApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val ruleBookClient: RuleBookClient,
) {
    fun getAbilities(roomId: UUID): List<AbilityDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val abilitiesForRoom: List<AbilityDto> = ruleBookClient.getAbilitiesForRoom(roomId) ?: return listOf()
        return abilitiesForRoom
    }


}
