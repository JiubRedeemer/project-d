package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entities.RoomUserInvite
import com.jiubredeemer.dal.entities.RoomUserInviteKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomUserInviteRepository : JpaRepository<RoomUserInvite, RoomUserInviteKey> {

    @Query("select r from RoomUserInvite r where r.id.roomId = ?1 and r.id.invitedUserId = ?2")
    fun findByRoomIdAndInvitedUserId(roomId: UUID, invitedUserId: UUID): Optional<RoomUserInvite>

}
