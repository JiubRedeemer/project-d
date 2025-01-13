package com.jiubredeemer.dal.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "room", schema = "core")
open class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    open var id: UUID? = null

    @NotNull
    @ColumnDefault("'Комната'::text")
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    open var name: String? = null

    @Column(name = "description", nullable = true, length = Integer.MAX_VALUE)
    open var description: String? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "owner_id", nullable = false)
    open var owner: User? = null

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "create_datetime", nullable = false)
    open var createDatetime: Timestamp? = null

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "update_datetime", nullable = false)
    open var updateDatetime: Timestamp? = null

    @Column(name = "delete_datetime")
    open var deleteDatetime: Timestamp? = null

    @NotNull
    @Column(name = "last_activity_datetime", nullable = false)
    open var lastActivityDatetime: Timestamp? = null

    @OneToMany(mappedBy = "room", cascade = [CascadeType.ALL])
    open var roomUsers: MutableSet<RoomUser> = mutableSetOf()
}
