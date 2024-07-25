package com.jiubredeemer.dal.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*

@Embeddable
open class RoomUserInviteKey : Serializable {
    @NotNull
    @Column(name = "room_id", nullable = false)
    open var roomId: UUID? = null

    @NotNull
    @Column(name = "owner_id", nullable = false)
    open var ownerId: UUID? = null

    @NotNull
    @Column(name = "invited_user_id", nullable = false)
    open var invitedUserId: UUID? = null
    override fun hashCode(): Int = Objects.hash(roomId, ownerId, invitedUserId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as RoomUserInviteKey

        return roomId == other.roomId &&
                ownerId == other.ownerId &&
                invitedUserId == other.invitedUserId
    }

    companion object {
        private const val serialVersionUID = 8018299786039028048L
    }
}
