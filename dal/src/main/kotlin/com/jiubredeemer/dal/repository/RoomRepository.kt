package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entities.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomRepository : JpaRepository<Room, UUID> {

    @Query("select r from Room r inner join r.roomUsers roomUsers where roomUsers.user.id = :userId order by roomUsers.createDatetime desc")
    fun findAllByUserId(@Param("userId") userId: UUID): List<Room>


    @Query("select r from Room r where r.id = :id and r.owner.id = :ownerId")
    fun findByIdAndOwnerId(@Param("id") id: UUID, @Param("ownerId") ownerId: UUID): Room?

}
