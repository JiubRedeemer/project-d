package com.jiubredeemer.auth.service

import com.jiubredeemer.auth.model.request.UserRegistration
import com.jiubredeemer.auth.converter.UserRegistrationConverter
import com.jiubredeemer.auth.validators.UserValidator
import com.jiubredeemer.dal.entities.User
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class RegistrationService(
    private val userValidator: UserValidator,
    private val userRegistrationConverter: UserRegistrationConverter,
    private val userRepository: UserRepository
) {
    fun registration(registrationRequest: UserRegistration): User? {
        userValidator.onRegistration(registrationRequest)
        val userModel: User = userRegistrationConverter.convertToUser(registrationRequest)
        userModel.registrationDate = Timestamp.valueOf(LocalDateTime.now())
        return userRepository.save(userModel)
    }
}
