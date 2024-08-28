package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entities.RoomUser
import com.jiubredeemer.dal.entities.RoomsUserKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomsUserRepository : JpaRepository<RoomUser, RoomsUserKey> {

    @Query("select ru from RoomUser ru where ru.room.id = :roomId and ru.user.id = :userId")
    fun findByRoomAndUser(@Param("roomId") roomId: UUID,@Param("userId") userId: UUID): RoomUser?

}
