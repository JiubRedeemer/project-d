package com.jiubredeemer.dal.entities

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.ColumnDefault
import java.sql.Timestamp

@Entity
@Table(name = "room_user_invites", schema = "core")
open class RoomUserInvite {
    @EmbeddedId
    open var id: RoomUserInviteKey? = null

    @MapsId("roomId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    open var room: Room? = null

    @MapsId("ownerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    open var owner: User? = null

    @MapsId("invitedUserId")
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
