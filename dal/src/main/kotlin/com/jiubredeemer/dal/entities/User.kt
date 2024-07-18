package com.jiubredeemer.dal.entities

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Entity
@Table(name = "users", schema = "core")
open class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    open var id: UUID? = null

    @Column(name = "username", nullable = false)
    open var username: String? = null

    @Column(name = "email", nullable = false)
    open var email: String? = null

    @Column(name = "password", nullable = false)
    open var password: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    open var roles: List<Role> = listOf(Role.USER)
}

enum class Role {
    USER, ADMIN
}
