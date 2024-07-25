package com.jiubredeemer.dal.models

import com.jiubredeemer.dal.entities.User
import java.util.*

data class UserDto(
    var id: UUID? = null,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var roles: List<User.Role>? = ArrayList()
)
