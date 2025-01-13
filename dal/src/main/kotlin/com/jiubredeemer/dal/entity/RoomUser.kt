package com.jiubredeemer.dal.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.sql.Timestamp

@Entity
@Table(name = "room_user", schema = "core")
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
    @ColumnDefault("now()")
    @Column(name = "create_datetime", nullable = false)
    open var createDatetime: Timestamp? = null

    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "roles", nullable = false)
    open var roles: List<Role>? = null

    enum class Role {
        MASTER, PLAYER, MODERATOR
    }
}
