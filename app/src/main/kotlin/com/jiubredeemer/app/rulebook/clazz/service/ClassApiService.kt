package com.jiubredeemer.app.rulebook.clazz.service

import com.jiubredeemer.app.integration.rulebook.RuleBookClient
import com.jiubredeemer.app.integration.rulebook.dto.clazz.ClazzDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.app.rulebook.clazz.model.ClassCreateInfoDto
import com.jiubredeemer.auth.service.AccessChecker
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClassApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val ruleBookClient: RuleBookClient
) {
    fun getClasses(roomId: UUID): List<ClassCreateInfoDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val classesForRoom: List<ClazzDto> = ruleBookClient.getClassesForRoom(roomId) ?: return listOf()
        return classesForRoom.map { ClassCreateInfoDto(it.name, it.description ?: "", it.code, it.stats) }
    }
}
