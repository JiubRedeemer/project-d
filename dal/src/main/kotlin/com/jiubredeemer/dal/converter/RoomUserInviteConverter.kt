package com.jiubredeemer.dal.converter

import com.jiubredeemer.dal.entity.Room
import com.jiubredeemer.dal.entity.RoomUser
import com.jiubredeemer.dal.entity.RoomUserInvite
import com.jiubredeemer.dal.entity.User
import com.jiubredeemer.dal.model.RoomUserInviteDto
import org.springframework.stereotype.Component

@Component
class RoomUserInviteConverter {
    fun toEntity(room: Room, owner: User, invitedUser: User, role: RoomUser.Role): RoomUserInvite {
        val entity = RoomUserInvite()
        entity.room = room
        entity.owner = owner
        entity.invitedUser = invitedUser
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
