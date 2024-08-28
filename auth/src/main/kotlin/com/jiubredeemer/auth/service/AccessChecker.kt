package com.jiubredeemer.auth.service

import com.jiubredeemer.dal.entities.User
import com.jiubredeemer.dal.models.UserDto
import com.jiubredeemer.dal.service.UserService
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class AccessChecker(
    private val userService: UserService,
) {
    fun hasAnyRoleOrThrow(roles: List<User.Role>) {
        val hasRole = getCurrentUser().roles!!.any { role: User.Role -> roles.contains(role) }
        if (!hasRole) {
            throw AccessDeniedException("Not enough role to access this resource")
        }
    }

    fun getCurrentUser(): UserDto {
        val email = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username
        return userService.getByEmail(email) ?: throw AccessDeniedException("No authed user")
    }
}
