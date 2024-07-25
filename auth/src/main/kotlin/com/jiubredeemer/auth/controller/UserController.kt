package com.jiubredeemer.auth.controller

import com.jiubredeemer.auth.annotations.HasRoleOrThrow
import com.jiubredeemer.dal.models.UserDto
import com.jiubredeemer.dal.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @HasRoleOrThrow("ADMIN")
    @GetMapping
    fun getAll(): List<UserDto> {
        return userService.getAllUsers()
    }
}
