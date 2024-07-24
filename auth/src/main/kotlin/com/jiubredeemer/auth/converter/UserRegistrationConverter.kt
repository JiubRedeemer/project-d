package com.jiubredeemer.auth.converter

import com.jiubredeemer.auth.model.request.UserRegistration
import com.jiubredeemer.dal.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserRegistrationConverter {
    @Autowired
    private lateinit var encoder: PasswordEncoder

    fun convertToUser(registration: UserRegistration): User {
        val user = User()
        user.username = registration.username
        user.email = registration.email
        user.password = encoder.encode(registration.password)
        user.roles = listOf(User.Role.USER)
        return user
    }
}
