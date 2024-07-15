package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(@Param("usernameOrEmail") usernameOrEmail: String): User?
}