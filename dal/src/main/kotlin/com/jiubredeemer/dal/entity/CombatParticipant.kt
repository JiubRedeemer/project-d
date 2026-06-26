package com.jiubredeemer.dal.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.util.*

@Entity
@Table(name = "combat_participant", schema = "core")
open class CombatParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    open var session: CombatSession? = null

    @ColumnDefault("'CHARACTER'")
    @Column(name = "participant_type", nullable = false, length = 20)
    open var participantType: String = "CHARACTER"

    @Column(name = "reference_id")
    open var referenceId: UUID? = null

    @Column(name = "display_name", length = 255)
    open var displayName: String? = null

    @Column(name = "initiative")
    open var initiative: Int? = null

    @ColumnDefault("false")
    @Column(name = "is_ready", nullable = false)
    open var isReady: Boolean = false

    @ColumnDefault("1")
    @Column(name = "copy_index", nullable = false)
    open var copyIndex: Int = 1

    @Column(name = "sort_order")
    open var sortOrder: Int? = null

    @Column(name = "current_hp")
    open var currentHp: Int? = null

    @Column(name = "max_hp")
    open var maxHp: Int? = null

    @Column(name = "armory_class")
    open var armoryClass: Int? = null
}
