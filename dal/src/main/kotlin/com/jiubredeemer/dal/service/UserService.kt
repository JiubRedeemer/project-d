package com.jiubredeemer.dal.service

import com.jiubredeemer.dal.converter.UserConverter
import com.jiubredeemer.dal.models.UserDto
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userConverter: UserConverter
) {
    fun getAllUsers(): List<UserDto> {
        val findAll = userRepository.findAll()
        return findAll.map(userConverter::toDto).toList()
    }
}
