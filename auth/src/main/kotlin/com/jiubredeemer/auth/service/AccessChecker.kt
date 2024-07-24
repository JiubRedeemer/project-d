package com.jiubredeemer.auth.service

import com.jiubredeemer.dal.entities.User
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class AccessChecker(
    private val userRepository: UserRepository,
) {
    fun hasAnyRoleOrThrow(roles: List<User.Role>) {
        getCurrentUser().roles.any { role: User.Role -> roles.contains(role) }
    }

    fun getCurrentUser(): User {
        val email = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username
        return userRepository.findByEmail(email) ?: throw AccessDeniedException("No authed user")
    }
}
