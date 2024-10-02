package com.jiubredeemer.dal.entities

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.sql.Timestamp
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

    @Column(name = "registration_date", nullable = false)
    open var registrationDate: Timestamp? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    open var roles: List<Role> = listOf(Role.USER)

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    open var roomUsers: MutableSet<RoomUser> = mutableSetOf()

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL])
    open var rooms: MutableSet<Room> = mutableSetOf()

    enum class Role {
        USER, ADMIN
    }
}


