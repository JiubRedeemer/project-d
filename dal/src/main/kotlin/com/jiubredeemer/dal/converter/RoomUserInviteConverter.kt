package com.jiubredeemer.dal.converter

import com.jiubredeemer.dal.entities.Room
import com.jiubredeemer.dal.entities.RoomUserInvite
import com.jiubredeemer.dal.entities.User
import com.jiubredeemer.dal.models.RoomUserInviteDto
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class RoomUserInviteConverter {
    fun toEntity(room: Room, owner: User, invitedUser: User): RoomUserInvite {
        val now = Timestamp.valueOf(LocalDateTime.now())
        val entity = RoomUserInvite()
        entity.room = room
        entity.owner = owner
        entity.invitedUser = invitedUser
        entity.createDatetime = now
        return entity
    }

    fun toDto(entity: RoomUserInvite): RoomUserInviteDto {
        val model = RoomUserInviteDto()
        BeanUtils.copyProperties(entity, model)
        return model
    }
}
