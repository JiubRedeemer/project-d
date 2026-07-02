package com.jiubredeemer.dal.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Entity
@Table(name = "room_schedule", schema = "core")
open class RoomSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    open var room: Room? = null

    @ColumnDefault("false")
    @Column(name = "is_recurring", nullable = false)
    open var isRecurring: Boolean = false

    @Column(name = "session_datetime")
    open var sessionDatetime: LocalDateTime? = null

    @Column(name = "recurrence_type")
    open var recurrenceType: String? = null

    @Column(name = "day_of_week")
    open var dayOfWeek: Int? = null

    @Column(name = "day_of_month")
    open var dayOfMonth: Int? = null

    @Column(name = "session_time")
    open var sessionTime: LocalTime? = null
}
