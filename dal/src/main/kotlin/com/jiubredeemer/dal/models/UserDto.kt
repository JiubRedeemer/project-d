package com.jiubredeemer.dal.models

import com.jiubredeemer.dal.entities.User
import java.util.*

data class UserDto(
    val id: UUID? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val roles: List<User.Role>? = ArrayList()
)
