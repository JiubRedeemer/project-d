package com.jiubredeemer.dal.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "combat_session", schema = "core")
open class CombatSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    open var room: Room? = null

    @ColumnDefault("'PREPARING'")
    @Column(name = "state", nullable = false, length = 20)
    open var state: String = "PREPARING"

    @ColumnDefault("1")
    @Column(name = "round", nullable = false)
    open var round: Int = 1

    @ColumnDefault("0")
    @Column(name = "current_turn_index", nullable = false)
    open var currentTurnIndex: Int = 0

    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    open var createdAt: Timestamp? = null

    @OneToMany(mappedBy = "session", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var participants: MutableList<CombatParticipant> = mutableListOf()
}
