package com.jiubredeemer.dal.service

import com.jiubredeemer.dal.converter.UserConverter
import com.jiubredeemer.dal.models.UserDto
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userConverter: UserConverter
) {
    fun getAllUsers(): List<UserDto> {
        val findAll = userRepository.findAll()
        return findAll.map(userConverter::toDto).toList()
    }

    fun getByUsername(username: String): UserDto? {
        return userRepository.findByUsername(username)?.let { userConverter.toDto(it) }
    }

    fun getByEmail(email: String): UserDto? {
        return userRepository.findByEmail(email)?.let { userConverter.toDto(it) }
    }

    fun readById(id: UUID): UserDto? {
        return userRepository.getReferenceById(id).let { userConverter.toDto(it) }
    }
}
