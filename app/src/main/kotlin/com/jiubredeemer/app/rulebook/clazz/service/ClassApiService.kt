package com.jiubredeemer.app.rulebook.clazz.service

import com.jiubredeemer.app.integration.rulebook.RuleBookClient
import com.jiubredeemer.app.integration.rulebook.dto.clazz.ClazzGroupDto
import com.jiubredeemer.app.integration.rulebook.dto.clazz.ClazzDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.app.rulebook.clazz.model.ClassCreateInfoDto
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClassApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val ruleBookClient: RuleBookClient
) {
    fun getGroupedClasses(roomId: UUID): List<ClazzGroupDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getGroupedClassesForRoom(roomId) ?: listOf()
    }

    fun getClasses(roomId: UUID): List<ClassCreateInfoDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val classesForRoom: List<ClazzDto> = ruleBookClient.getClassesForRoom(roomId) ?: return listOf()
        return classesForRoom.map {
            ClassCreateInfoDto(it.name, it.description ?: "", it.code, it.groupCode, it.imgUrl, it.stats)
        }
    }

    fun createClass(roomId: UUID, clazzDto: ClazzDto): ClazzDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.createClass(clazzDto) ?: throw NotFoundException("Failed to create class")
    }

    fun getRootClasses(roomId: UUID): List<ClazzDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getRootClassesForRoom(roomId) ?: listOf()
    }

    fun getClassByCode(roomId: UUID, code: String): ClazzDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getClassByCode(roomId, code)
            ?: throw NotFoundException("Class not found by code: $code")
    }

    fun getSubClassesForRoom(roomId: UUID, code: String): List<ClazzDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return ruleBookClient.getSubClassesForRoom(roomId, code) ?: listOf()
    }
}
