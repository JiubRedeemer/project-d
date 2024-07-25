package com.jiubredeemer.dal.converter

import com.jiubredeemer.dal.entities.Room
import com.jiubredeemer.dal.models.RoomDto
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class RoomConverter {

    fun toEntity(model: RoomDto): Room {
        val now = Timestamp.valueOf(LocalDateTime.now())

        val entity = Room()
        entity.name = model.name
        entity.createDatetime = now
        entity.updateDatetime = now
        entity.lastActivityDatetime = now
        return entity
    }

    fun toDto(entity: Room): RoomDto {
        val model = RoomDto()
        BeanUtils.copyProperties(entity, model)
        return model
    }
}
