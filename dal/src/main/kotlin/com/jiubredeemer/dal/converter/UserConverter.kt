package com.jiubredeemer.dal.converter

import com.jiubredeemer.dal.entities.User
import com.jiubredeemer.dal.models.UserDto
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component

@Component
class UserConverter {

    fun toEntity(model: UserDto): User {
        val entity = User()
        BeanUtils.copyProperties(model, entity)
        return entity
    }

    fun toDto(entity: User): UserDto {
        val model = UserDto()
        BeanUtils.copyProperties(entity, model)
        return model
    }
}
