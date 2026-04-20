package com.jiubredeemer.auth.service

import com.jiubredeemer.auth.exception.ErrorCode
import com.jiubredeemer.auth.exception.ValidationException
import com.jiubredeemer.auth.model.request.ChangeEmailRequest
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeEmailService(
    private val accessChecker: AccessChecker,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val registrationVerificationCodeService: RegistrationVerificationCodeService,
) {
    @Transactional
    fun sendVerificationCode(newEmail: String) {
        registrationVerificationCodeService.sendVerificationCode(newEmail)
    }

    @Transactional
    fun changeEmail(request: ChangeEmailRequest) {
        val currentUser = accessChecker.getCurrentUser()
        val userId = currentUser.id ?: throw ValidationException(ErrorCode.OTHER, "userId")
        val user = userRepository.findById(userId).orElseThrow {
            ValidationException(ErrorCode.USER_NOT_FOUND_BY_EMAIL, "email")
        }

        val trimmedPassword = request.currentPassword.trim()
        if (trimmedPassword.isEmpty()) {
            throw ValidationException(ErrorCode.MISSED_REQUIRED_FIELD, "currentPassword")
        }
        if (!passwordEncoder.matches(trimmedPassword, user.password!!)) {
            throw ValidationException(ErrorCode.INVALID_PASSWORD, "currentPassword")
        }

        val normalizedEmail = request.newEmail.trim()
        registrationVerificationCodeService.verifyAndConsume(normalizedEmail, request.verificationCode)

        val existingUser = userRepository.findByEmailIgnoreCase(normalizedEmail)
        if (existingUser != null && existingUser.id != user.id) {
            throw ValidationException(ErrorCode.USER_EXISTS_BY_EMAIL, "newEmail")
        }

        user.email = normalizedEmail
        userRepository.save(user)
    }
}
