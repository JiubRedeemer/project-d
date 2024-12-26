package com.jiubredeemer.app.rulebook.classes.service

import com.jiubredeemer.app.integration.RuleBookClient
import com.jiubredeemer.app.integration.dto.clazz.ClassDto
import com.jiubredeemer.app.rooms.service.RoomAccessChecker
import com.jiubredeemer.app.rulebook.classes.model.ClassCreateInfoDto
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
        val classesForRoom: List<ClassDto> = ruleBookClient.getClassesForRoom(roomId) ?: return listOf()
        return classesForRoom.map { ClassCreateInfoDto(it.name, it.description ?: "", it.code, it.stats) }
    }
}
