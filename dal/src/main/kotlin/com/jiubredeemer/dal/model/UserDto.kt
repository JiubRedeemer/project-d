package com.jiubredeemer.dal.model

import com.jiubredeemer.dal.entity.User
import java.util.*

data class UserDto(
    var id: UUID? = null,
    var username: String? = null,
    var email: String? = null,
    var registrationDate: String? = null,
    var password: String? = null,
    var roles: List<User.Role>? = ArrayList()
)
