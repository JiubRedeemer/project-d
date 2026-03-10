package com.jiubredeemer.auth.controller

import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.dal.model.UserDto
import com.jiubredeemer.dal.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService, private val accessChecker: AccessChecker) {

    @HasRoleOrThrow("ADMIN")
    @GetMapping
    fun getAll(): List<UserDto> {
        return userService.getAllUsers()
    }

    @HasRoleOrThrow("ADMIN", "USER")
    @GetMapping("/myId")
    fun getMe(): UUID? {
        return accessChecker.getCurrentUser().id
    }
}
