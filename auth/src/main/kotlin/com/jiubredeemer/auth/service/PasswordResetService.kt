package com.jiubredeemer.auth.service

import com.jiubredeemer.auth.exception.ErrorCode
import com.jiubredeemer.auth.exception.ValidationException
import com.jiubredeemer.auth.model.request.PasswordResetRequest
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PasswordResetService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val passwordResetVerificationCodeService: PasswordResetVerificationCodeService,
) {
    @Transactional
    fun resetPassword(request: PasswordResetRequest) {
        if (request.password != request.matchingPassword) {
            throw ValidationException(ErrorCode.PASSWORD_DO_NOT_MATCH, "passwords")
        }

        passwordResetVerificationCodeService.verifyAndConsume(request.email, request.verificationCode)

        val user = userRepository.findByEmailIgnoreCase(request.email.trim())
            ?: throw ValidationException(ErrorCode.USER_NOT_FOUND_BY_EMAIL, "email")

        user.password = passwordEncoder.encode(request.password)
        userRepository.save(user)
    }
}
