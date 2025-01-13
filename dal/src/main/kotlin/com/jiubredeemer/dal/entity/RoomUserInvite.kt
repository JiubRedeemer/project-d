package com.jiubredeemer.dal.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.ColumnDefault
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "room_user_invite", schema = "core")
open class RoomUserInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    open var room: Room? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    open var owner: User? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invited_user_id", nullable = false)
    open var invitedUser: User? = null

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "create_datetime", nullable = false)
    open var createDatetime: Timestamp? = null

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = Integer.MAX_VALUE)
    open var role: RoomUser.Role? = null

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = Integer.MAX_VALUE)
    open var status: Status? = null

    enum class Status {
        PENDING, ACCEPTED, DECLINED, REVOKED
    }
}
