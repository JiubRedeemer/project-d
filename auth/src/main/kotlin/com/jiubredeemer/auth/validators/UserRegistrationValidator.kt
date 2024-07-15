package com.jiubredeemer.auth.validators

import com.jiubredeemer.auth.model.request.UserRegistration
import com.jiubredeemer.auth.exceptions.ErrorCode
import com.jiubredeemer.auth.exceptions.ValidationException
import org.springframework.stereotype.Component

@Component
class UserRegistrationValidator {
    fun onRegistration(registrationRequest: UserRegistration) {
        validateMatchingPassword(registrationRequest)
    }

    private fun validateMatchingPassword(registrationRequest: UserRegistration) {
        if (registrationRequest.password != registrationRequest.matchingPassword) {
            throw ValidationException(ErrorCode.PASSWORD_DO_NOT_MATCH);
        }
    }
}
