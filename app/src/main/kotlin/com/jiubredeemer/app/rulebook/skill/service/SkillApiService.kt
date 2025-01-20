package com.jiubredeemer.app.rulebook.skill.service

import com.jiubredeemer.app.integration.rulebook.RuleBookClient
import com.jiubredeemer.app.integration.rulebook.dto.skill.SkillDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.app.rulebook.skill.model.SkillResponse
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class SkillApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val ruleBookClient: RuleBookClient,
) {
    fun getSkills(roomId: UUID): List<SkillResponse> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val skillsForRoom: List<SkillDto> = ruleBookClient.getSkillsForRoom(roomId) ?: return listOf()
        return skillsForRoom.map { SkillResponse(it.name, it.code, it.ability) }
    }

    fun getSkillByCode(roomId: UUID, code: String): SkillResponse {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val skillByCode: SkillDto =
            ruleBookClient.getSkillByCodeForRoom(roomId, code) ?: throw NotFoundException("Skill not found by code")
        return SkillResponse(skillByCode.name, skillByCode.code, skillByCode.ability)
    }

    fun getSkillByClass(roomId: UUID, classCode: String): List<SkillResponse> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val skillsForRoom: List<SkillDto> = ruleBookClient.getSkillsByClassForRoom(roomId, classCode) ?: return listOf()
        return skillsForRoom.map { SkillResponse(it.name, it.code, it.ability) }
    }
}
