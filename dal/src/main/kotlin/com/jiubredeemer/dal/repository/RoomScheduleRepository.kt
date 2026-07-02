package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entity.RoomSchedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomScheduleRepository : JpaRepository<RoomSchedule, UUID> {

    @Query("select s from RoomSchedule s where s.room.id = :roomId")
    fun findByRoomId(@Param("roomId") roomId: UUID): RoomSchedule?

    @Query("select s from RoomSchedule s where s.room.id in :roomIds")
    fun findByRoomIds(@Param("roomIds") roomIds: List<UUID>): List<RoomSchedule>
}
