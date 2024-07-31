package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entities.RoomUserInvite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomUserInviteRepository : JpaRepository<RoomUserInvite, UUID> {


    @Query("select r from RoomUserInvite r where r.invitedUser.id = ?1")
    fun findByInvitedUserId(id: UUID): List<RoomUserInvite>


    @Query("select r from RoomUserInvite r where r.room.id = ?1 and r.invitedUser.id = ?2")
    fun findByRoomIdAndInvitedUserId(id: UUID, invitedUserId: UUID): RoomUserInvite?
}
