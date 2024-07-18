package com.jiubredeemer.auth.validators

import com.jiubredeemer.auth.exceptions.ErrorCode
import com.jiubredeemer.auth.exceptions.ValidationException
import com.jiubredeemer.auth.model.request.UserRegistration
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val userRepository: UserRepository,
) {
    fun onRegistration(registrationRequest: UserRegistration) {
        validateMatchingPassword(registrationRequest)
        validateUniqueByEmail(registrationRequest)
        validateUniqueByUsername(registrationRequest)
    }

    private fun validateUniqueByEmail(registrationRequest: UserRegistration) {
        if (userRepository.findByEmail(registrationRequest.email) != null) {
            throw ValidationException(ErrorCode.USER_EXISTS_BY_EMAIL)
        }
    }

    private fun validateUniqueByUsername(registrationRequest: UserRegistration) {
        if (userRepository.findByUsername(registrationRequest.email) != null) {
            throw ValidationException(ErrorCode.USER_EXISTS_BY_USERNAME)
        }
    }

    private fun validateMatchingPassword(registrationRequest: UserRegistration) {
        if (registrationRequest.password != registrationRequest.matchingPassword) {
            throw ValidationException(ErrorCode.PASSWORD_DO_NOT_MATCH)
        }
    }
}
