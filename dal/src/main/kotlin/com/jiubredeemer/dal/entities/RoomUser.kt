package com.jiubredeemer.dal.entities

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "rooms_users", schema = "core")
open class RoomUser {
    @EmbeddedId
    open var id: RoomsUserKey? = null

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @MapsId("roomId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    open var room: Room? = null

    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "roles", nullable = false)
    open var roles: List<Role>? = null

    enum class Role {
        MASTER, PLAYER, MODERATOR
    }
}
