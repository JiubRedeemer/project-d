package com.jiubredeemer.auth.service

import com.jiubredeemer.dal.models.UserDto
import com.jiubredeemer.dal.service.UserService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(
    private val userService: UserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userService.getByEmail(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Not found!")

    private fun UserDto.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(*this.roles!!.map { it.name }.toTypedArray())
            .build()


}
