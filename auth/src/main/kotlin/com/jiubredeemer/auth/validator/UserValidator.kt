package com.jiubredeemer.auth.validator

import com.jiubredeemer.auth.exception.ErrorCode
import com.jiubredeemer.auth.exception.ValidationException
import com.jiubredeemer.auth.model.request.UserRegistration
import com.jiubredeemer.dal.service.UserService
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val userService: UserService,
) {
    fun onRegistration(registrationRequest: UserRegistration) {
        validateMatchingPassword(registrationRequest)
        validateUniqueByEmail(registrationRequest)
        validateUniqueByUsername(registrationRequest)
    }

    private fun validateUniqueByEmail(registrationRequest: UserRegistration) {
        if (userService.getByEmail(registrationRequest.email) != null) {
            throw ValidationException(ErrorCode.USER_EXISTS_BY_EMAIL, "email")
        }
    }

    private fun validateUniqueByUsername(registrationRequest: UserRegistration) {
        if (userService.getByUsername(registrationRequest.email) != null) {
            throw ValidationException(ErrorCode.USER_EXISTS_BY_USERNAME, "username")
        }
    }

    private fun validateMatchingPassword(registrationRequest: UserRegistration) {
        if (registrationRequest.password != registrationRequest.matchingPassword) {
            throw ValidationException(ErrorCode.PASSWORD_DO_NOT_MATCH, "passwords")
        }
    }
}
