package com.jiubredeemer.auth.service

import com.jiubredeemer.auth.model.request.UserRegistration
import com.jiubredeemer.auth.converter.UserRegistrationConverter
import com.jiubredeemer.auth.validators.UserRegistrationValidator
import com.jiubredeemer.dal.entities.User
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class RegistrationService(
    private val userRegistrationValidator: UserRegistrationValidator,
    private val userRegistrationConverter: UserRegistrationConverter,
    private val userRepository: UserRepository
) {
    fun registration(registrationRequest: UserRegistration): User? {
        userRegistrationValidator.onRegistration(registrationRequest)
        val userModel: User = userRegistrationConverter.convertToUser(registrationRequest)
        return userRepository.save(userModel)
    }
}
