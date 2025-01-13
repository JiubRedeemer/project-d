package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {

    @Query("select u from User u where u.email = :email")
    fun findByEmail(@Param("email") email: String): User?

    @Query("select u from User u where u.username = :username")
    fun findByUsername(username: String): User?
}
