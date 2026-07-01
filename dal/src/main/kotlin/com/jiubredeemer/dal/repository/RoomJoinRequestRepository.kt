package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entity.RoomJoinRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomJoinRequestRepository : JpaRepository<RoomJoinRequest, UUID> {

    @Query("select r from RoomJoinRequest r join fetch r.room join fetch r.requester where r.room.owner.id = :ownerId and r.status = 'PENDING' order by r.createDatetime desc")
    fun findPendingByRoomOwnerId(@Param("ownerId") ownerId: UUID): List<RoomJoinRequest>

    @Query("select count(r) from RoomJoinRequest r where r.room.owner.id = :ownerId and r.status = 'PENDING'")
    fun countPendingByRoomOwnerId(@Param("ownerId") ownerId: UUID): Long

    @Query("select r from RoomJoinRequest r where r.room.id = :roomId and r.requester.id = :requesterId and r.status = 'PENDING'")
    fun findPendingByRoomIdAndRequesterId(
        @Param("roomId") roomId: UUID,
        @Param("requesterId") requesterId: UUID
    ): RoomJoinRequest?
}
