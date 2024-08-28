package com.jiubredeemer.dal.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*

@Embeddable
open class RoomsUserKey : Serializable {
    @NotNull
    @Column(name = "user_id", nullable = false)
    open var userId: UUID? = null

    @NotNull
    @Column(name = "room_id", nullable = false)
    open var roomId: UUID? = null

    override fun hashCode(): Int = Objects.hash(userId, roomId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as RoomsUserKey

        return userId == other.userId &&
                roomId == other.roomId
    }

    companion object {
        private const val serialVersionUID = 2599508344623602987L
    }
}

