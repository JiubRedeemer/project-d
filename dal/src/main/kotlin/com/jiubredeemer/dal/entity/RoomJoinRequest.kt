package com.jiubredeemer.dal.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.ColumnDefault
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "room_join_request", schema = "core")
open class RoomJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    open var id: UUID? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    open var room: Room? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    open var requester: User? = null

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = Integer.MAX_VALUE)
    open var status: Status = Status.PENDING

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "create_datetime", nullable = false)
    open var createDatetime: Timestamp? = null

    enum class Status {
        PENDING, ACCEPTED, DECLINED
    }
}
