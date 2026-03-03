package com.jiubredeemer.app.rulebook.background.service

import com.jiubredeemer.app.integration.dto.RuleTypeEnum
import com.jiubredeemer.app.integration.rulebook.RuleBookClient
import com.jiubredeemer.app.integration.rulebook.dto.background.BackgroundDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class BackgroundApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val ruleBookClient: RuleBookClient
) {
    fun getBackgrounds(roomId: UUID, forceRuleTypeEnum: RuleTypeEnum?): List<BackgroundDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getBackgroundsForRoom(roomId, forceRuleTypeEnum) ?: listOf()
    }

    fun getBackgroundByCode(roomId: UUID, code: String, forceRuleTypeEnum: RuleTypeEnum?): BackgroundDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getBackgroundByCode(roomId, code, forceRuleTypeEnum)
            ?: throw NotFoundException("Background not found by code: $code")
    }

    fun createBackground(roomId: UUID, backgroundDto: BackgroundDto): BackgroundDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.createBackground(backgroundDto)
            ?: throw NotFoundException("Failed to create background")
    }
}
