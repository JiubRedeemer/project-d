package com.jiubredeemer.dal.entities

import jakarta.persistence.*
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

    @Column(name = "salt", nullable = false)
    open var salt: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    open var role: Role = Role.USER
}

enum class Role {
    USER, ADMIN
}
