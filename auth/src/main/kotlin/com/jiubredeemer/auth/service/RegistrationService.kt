package com.jiubredeemer.auth.service

import com.jiubredeemer.auth.exception.ErrorCode
import com.jiubredeemer.auth.exception.ValidationException
import com.jiubredeemer.auth.model.request.UserRegistration
import com.jiubredeemer.auth.converter.UserRegistrationConverter
import com.jiubredeemer.auth.validator.UserValidator
import com.jiubredeemer.dal.entity.User
import com.jiubredeemer.dal.repository.UserRepository
import com.jiubredeemer.dal.service.RoomUserInviteService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class RegistrationService(
    private val userValidator: UserValidator,
    private val userRegistrationConverter: UserRegistrationConverter,
    private val userRepository: UserRepository,
    private val roomUserInviteService: RoomUserInviteService,
    private val registrationVerificationCodeService: RegistrationVerificationCodeService,
) {
    @Transactional
    fun registration(registrationRequest: UserRegistration): User? {
        userValidator.onRegistration(registrationRequest)
        val hasInviteToken = registrationRequest.roomInviteToken?.trim()?.isNotEmpty() == true
        if (!hasInviteToken) {
            val code = registrationRequest.verificationCode?.trim()
            if (code.isNullOrEmpty()) {
                throw ValidationException(ErrorCode.MISSED_REQUIRED_FIELD, "verificationCode")
            }
            registrationVerificationCodeService.verifyAndConsume(registrationRequest.email, code)
        }
        val userModel: User = userRegistrationConverter.convertToUser(registrationRequest)
        userModel.registrationDate = Timestamp.valueOf(LocalDateTime.now())
        val saved = userRepository.save(userModel)
        registrationRequest.roomInviteToken?.trim()?.takeIf { it.isNotEmpty() }?.let { token ->
            roomUserInviteService.claimPendingInviteByToken(token, saved.id!!, saved.email!!)
        }
        return saved
    }
}
