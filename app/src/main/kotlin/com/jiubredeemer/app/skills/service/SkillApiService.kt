package com.jiubredeemer.app.skills.service

import com.jiubredeemer.app.abilities.mapper.AbilityMapper
import com.jiubredeemer.app.integration.RuleBookClient
import com.jiubredeemer.app.integration.dto.skill.SkillDto
import com.jiubredeemer.app.rooms.service.RoomAccessChecker
import com.jiubredeemer.app.skills.model.SkillResponse
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exceptions.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class SkillApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val ruleBookClient: RuleBookClient,
    private val abilityMapper: AbilityMapper
) {
    fun getSkills(roomId: UUID): List<SkillResponse> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val skillsForRoom: List<SkillDto> = ruleBookClient.getSkillsForRoom(roomId) ?: return listOf()
        return skillsForRoom.map { SkillResponse(it.name, it.code, abilityMapper.mapToResponse(it.ability)) }
    }

    fun getSkillByCode(roomId: UUID, code: String): SkillResponse {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val skillByCode: SkillDto =
            ruleBookClient.getSkillByCodeForRoom(roomId, code) ?: throw NotFoundException("Skill not found by code")
        return SkillResponse(skillByCode.name, skillByCode.code, abilityMapper.mapToResponse(skillByCode.ability));
    }

    fun getSkillByClass(roomId: UUID, classCode: String): List<SkillResponse> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val skillsForRoom: List<SkillDto> = ruleBookClient.getSkillsByClassForRoom(roomId, classCode) ?: return listOf()
        return skillsForRoom.map { SkillResponse(it.name, it.code, abilityMapper.mapToResponse(it.ability)) }
    }
}
