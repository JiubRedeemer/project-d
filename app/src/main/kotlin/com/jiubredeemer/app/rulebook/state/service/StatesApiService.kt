package com.jiubredeemer.app.rulebook.state.service

import com.jiubredeemer.app.integration.rulebook.RuleBookClient
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.app.rulebook.state.model.StateDto
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class StatesApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val ruleBookClient: RuleBookClient,
) {
    fun getStates(roomId: UUID): List<StateDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val statesForRoom: List<StateDto> = ruleBookClient.getStatesForRoom(roomId) ?: return listOf()
        return statesForRoom
    }

    fun getStateByCode(roomId: UUID, code: String): StateDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val stateForRoom: StateDto =
            ruleBookClient.getStateByCodeForRoom(roomId, code) ?: throw NotFoundException("State not found by code")
        return stateForRoom
    }
}
