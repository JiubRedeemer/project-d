package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entity.RoomUserInvite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomUserInviteRepository : JpaRepository<RoomUserInvite, UUID> {


    @Query("select r from RoomUserInvite r where r.invitedUser.id = ?1 ")
    fun findByInvitedUserId(id: UUID): List<RoomUserInvite>

    @Query(
        """
        select r from RoomUserInvite r
        where r.room.id = :roomId and r.status = 'PENDING' and (
            (r.invitedUser is not null and lower(r.invitedUser.email) = lower(:email))
            or (r.invitedUser is null and r.invitedEmail = :emailNorm)
        )
        """
    )
    fun findPendingByRoomIdAndEmail(
        @Param("roomId") roomId: UUID,
        @Param("email") email: String,
        @Param("emailNorm") emailNorm: String,
    ): RoomUserInvite?

    @Query(
        """
        select r from RoomUserInvite r
        where r.inviteToken = :token and r.status = 'PENDING' and r.invitedUser is null
        """
    )
    fun findPendingByInviteToken(@Param("token") token: String): RoomUserInvite?

    @Query("select r from RoomUserInvite r where r.invitedUser.id = ?1 and r.status = 'PENDING'")
    fun findPendingByInvitedUserId(id: UUID): List<RoomUserInvite>

    @Query("select count(1) from RoomUserInvite r where r.invitedUser.id = ?1")
    fun countByInvitedUserId(userId: UUID): Long

    @Query("select count(1) from RoomUserInvite r where r.invitedUser.id = ?1 and r.status = 'PENDING'")
    fun countPendingByInvitedUserId(userId: UUID): Long

    @Query("select r from RoomUserInvite r where r.id = ?1 and r.invitedUser.id = ?2")
    fun findByInviteIdAndInvitedUserId(id: UUID, invitedUserId: UUID): RoomUserInvite?


}
