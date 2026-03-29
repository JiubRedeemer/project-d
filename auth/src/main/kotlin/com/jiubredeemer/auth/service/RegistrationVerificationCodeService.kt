package com.jiubredeemer.auth.service

import com.jiubredeemer.auth.exception.ErrorCode
import com.jiubredeemer.auth.exception.ValidationException
import com.jiubredeemer.dal.entity.RegistrationEmailCode
import com.jiubredeemer.dal.repository.RegistrationEmailCodeRepository
import com.jiubredeemer.dal.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.sql.Timestamp
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class RegistrationVerificationCodeService(
    private val repository: RegistrationEmailCodeRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userService: UserService,
    private val mailService: RegistrationVerificationMailService,
) {
    private val secureRandom = SecureRandom()

    @Transactional
    fun sendVerificationCode(rawEmail: String) {
        val trimmed = rawEmail.trim()
        if (trimmed.isEmpty() || !trimmed.contains('@')) {
            throw ValidationException(ErrorCode.MISSED_REQUIRED_FIELD, "email")
        }
        val normalized = normalizeEmail(trimmed)
        if (userService.existsByEmailIgnoreCase(trimmed)) {
            throw ValidationException(ErrorCode.USER_EXISTS_BY_EMAIL, "email")
        }
        val existing = repository.findById(normalized).orElse(null)
        if (existing != null) {
            val elapsed = Duration.between(existing.createdAt!!.toInstant(), Instant.now()).seconds
            if (elapsed < COOLDOWN_SECONDS) {
                throw ValidationException(ErrorCode.VERIFICATION_CODE_COOLDOWN, "email")
            }
        }
        val plainCode = generateSixDigitCode()
        val entity = existing ?: RegistrationEmailCode()
        entity.email = normalized
        entity.codeHash = passwordEncoder.encode(plainCode)
        entity.expiresAt = Timestamp.from(Instant.now().plus(CODE_TTL_MINUTES, ChronoUnit.MINUTES))
        entity.createdAt = Timestamp.from(Instant.now())
        entity.attempts = 0
        repository.save(entity)
        mailService.sendRegistrationCode(trimmed, plainCode)
    }

    @Transactional
    fun verifyAndConsume(rawEmail: String, plainCode: String) {
        val normalized = normalizeEmail(rawEmail.trim())
        val trimmedCode = plainCode.trim()
        if (trimmedCode.isEmpty()) {
            throw ValidationException(ErrorCode.VERIFICATION_CODE_INVALID, "verificationCode")
        }
        val row = repository.findById(normalized).orElse(null)
            ?: throw ValidationException(ErrorCode.VERIFICATION_CODE_INVALID, "verificationCode")
        if (Instant.now().isAfter(row.expiresAt!!.toInstant())) {
            repository.delete(row)
            throw ValidationException(ErrorCode.VERIFICATION_CODE_EXPIRED, "verificationCode")
        }
        if (row.attempts >= MAX_ATTEMPTS) {
            repository.delete(row)
            throw ValidationException(ErrorCode.VERIFICATION_CODE_INVALID, "verificationCode")
        }
        if (!passwordEncoder.matches(trimmedCode, row.codeHash!!)) {
            row.attempts = row.attempts + 1
            repository.save(row)
            throw ValidationException(ErrorCode.VERIFICATION_CODE_INVALID, "verificationCode")
        }
        repository.delete(row)
    }

    private fun normalizeEmail(email: String): String = email.trim().lowercase(Locale.ROOT)

    private fun generateSixDigitCode(): String {
        val n = secureRandom.nextInt(900_000) + 100_000
        return n.toString()
    }

    companion object {
        private const val COOLDOWN_SECONDS = 60L
        private const val CODE_TTL_MINUTES = 15L
        private const val MAX_ATTEMPTS = 5
    }
}
