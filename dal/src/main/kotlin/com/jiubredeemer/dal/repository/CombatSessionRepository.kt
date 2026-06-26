package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entity.CombatSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CombatSessionRepository : JpaRepository<CombatSession, UUID> {

    @Query("SELECT s FROM CombatSession s WHERE s.room.id = :roomId AND s.state IN ('PREPARING', 'ACTIVE') ORDER BY s.createdAt DESC")
    fun findActiveByRoomId(roomId: UUID): List<CombatSession>
}
