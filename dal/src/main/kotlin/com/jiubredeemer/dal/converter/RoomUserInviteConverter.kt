package com.jiubredeemer.dal.converter

import com.jiubredeemer.dal.entities.Room
import com.jiubredeemer.dal.entities.RoomUser
import com.jiubredeemer.dal.entities.RoomUserInvite
import com.jiubredeemer.dal.entities.User
import com.jiubredeemer.dal.models.RoomUserInviteDto
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class RoomUserInviteConverter {
    fun toEntity(room: Room, owner: User, invitedUser: User, role: RoomUser.Role): RoomUserInvite {
        val now = Timestamp.valueOf(LocalDateTime.now())
        val entity = RoomUserInvite()
        entity.room = room
        entity.owner = owner
        entity.invitedUser = invitedUser
        entity.createDatetime = now
        entity.role = role
        return entity
    }

    fun toDto(entity: RoomUserInvite): RoomUserInviteDto {
        val model = RoomUserInviteDto()
        model.id = entity.id
        model.roomId = entity.room?.id
        model.roomName = entity.room?.name
        model.roomDescription = entity.room?.description
        model.ownerId = entity.owner?.id
        model.ownerUsername = entity.owner?.username
        model.invitedUserEmail = entity.invitedUser?.email
        model.invitedUserId = entity.invitedUser?.id
        model.createDatetime = entity.createDatetime
        model.role = entity.role
        model.status = entity.status
        return model
    }
}
