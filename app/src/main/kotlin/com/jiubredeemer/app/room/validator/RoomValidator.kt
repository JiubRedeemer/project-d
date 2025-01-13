package com.jiubredeemer.app.room.validator

import com.jiubredeemer.app.room.model.request.CreateRoomRequest
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.dal.repository.RoomRepository
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

@Component
class RoomValidator(
    private val roomRepository: RoomRepository,
    private val accessChecker: AccessChecker
) {
    fun validateOnCreate(createRequest: CreateRoomRequest) {
        validateMandatoryFields(createRequest)
        validateAlreadyExists(createRequest)
        validateNameLength(createRequest.name)
    }

    private fun validateAlreadyExists(createRequest: CreateRoomRequest) {
        val findByNameAndOwnerId =
            roomRepository.findByNameAndOwnerId(createRequest.name!!, accessChecker.getCurrentUser().id!!)
        if (findByNameAndOwnerId.isPresent) {
            throw IllegalStateException("Room already exists")
        }

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
