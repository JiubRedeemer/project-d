package com.jiubredeemer.dal.converter

import com.jiubredeemer.dal.entity.User
import com.jiubredeemer.dal.model.UserDto
import org.springframework.stereotype.Component

@Component
class UserConverter {

    fun toEntity(model: UserDto): User {
        val entity = User()
        entity.id = model.id
        entity.email = model.email
        entity.username = model.username
        entity.password = model.password
        entity.roles = model.roles ?: ArrayList()
        return entity
    }

    fun toDto(entity: User): UserDto {
        val model = UserDto()
        model.id = entity.id
        model.email = entity.email
        model.username = entity.username
        model.password = entity.password
        model.roles = entity.roles
        return model
    }
}
