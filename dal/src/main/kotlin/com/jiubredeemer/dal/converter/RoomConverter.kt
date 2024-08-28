package com.jiubredeemer.dal.converter

import com.jiubredeemer.dal.entities.Room
import com.jiubredeemer.dal.models.RoomDto
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class RoomConverter(private val userConverter: UserConverter) {

    fun toEntity(model: RoomDto): Room {
        val now = Timestamp.valueOf(LocalDateTime.now())

        val entity = Room()
        entity.name = model.name
        entity.description = model.description
        entity.createDatetime = now
        entity.updateDatetime = now
        entity.lastActivityDatetime = now
        return entity
    }

    fun toDto(entity: Room): RoomDto {
        val model = RoomDto()
        model.id = entity.id
        model.name = entity.name
        model.description = entity.description
        model.createDatetime = entity.createDatetime
        model.lastActivityDatetime = entity.lastActivityDatetime
        model.updateDatetime = entity.updateDatetime
        model.deleteDatetime = entity.deleteDatetime
        if (entity.owner != null) {
            model.owner = userConverter.toDto(entity.owner!!)
        }
        BeanUtils.copyProperties(entity, model)
        return model
    }
}
