package com.jiubredeemer.app.rooms.validator

import com.jiubredeemer.app.rooms.model.request.CreateRoomRequest
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

@Component
class RoomValidator {
    fun validateOnCreate(createRequest: CreateRoomRequest) {
        validateMandatoryFields(createRequest)
        validateNameLength(createRequest.name)
    }

    private fun validateMandatoryFields(createRequest: CreateRoomRequest) {
        if (!StringUtils.hasText(createRequest.name)) {
            throw IllegalArgumentException("The 'name' field must not be null or empty")
        }
    }

    private fun validateNameLength(name: String?) {
        if (name!!.length <= 1) {
            throw IllegalArgumentException("The 'name' field must be longer than 1 character")
        }
    }
}
