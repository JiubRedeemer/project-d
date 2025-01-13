package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entity.RoomUserInvite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomUserInviteRepository : JpaRepository<RoomUserInvite, UUID> {


    @Query("select r from RoomUserInvite r where r.invitedUser.id = ?1 ")
    fun findByInvitedUserId(id: UUID): List<RoomUserInvite>

    @Query("select r from RoomUserInvite r where r.room.id = ?1 and r.invitedUser.email = ?2")
    fun findByRoomIdAndUserEmail(roomId: UUID, invitedUserEmail: String): RoomUserInvite?


    @Query("select r from RoomUserInvite r where r.invitedUser.id = ?1 and r.status = 'PENDING'")
    fun findPendingByInvitedUserId(id: UUID): List<RoomUserInvite>

    @Query("select count(1) from RoomUserInvite r where r.invitedUser.id = ?1")
    fun countByInvitedUserId(userId: UUID): Long

    @Query("select count(1) from RoomUserInvite r where r.invitedUser.id = ?1 and r.status = 'PENDING'")
    fun countPendingByInvitedUserId(userId: UUID): Long

    @Query("select r from RoomUserInvite r where r.id = ?1 and r.invitedUser.id = ?2")
    fun findByInviteIdAndInvitedUserId(id: UUID, invitedUserId: UUID): RoomUserInvite?


}
