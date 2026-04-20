package com.jiubredeemer.auth.service

import com.jiubredeemer.auth.configuration.RegistrationInviteMailProperties
import org.springframework.beans.factory.ObjectProvider
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

@Service
class RegistrationVerificationMailService(
    private val mailSenderProvider: ObjectProvider<JavaMailSender>,
    private val props: RegistrationInviteMailProperties,
) {
    fun sendRegistrationCode(toAddress: String, plainCode: String) {
        sendCode(
            toAddress = toAddress,
            plainCode = plainCode,
            subject = "Код подтверждения ProjectD",
            codePurpose = "регистрации",
        )
    }

    fun sendPasswordResetCode(toAddress: String, plainCode: String) {
        sendCode(
            toAddress = toAddress,
            plainCode = plainCode,
            subject = "Код для сброса пароля ProjectD",
            codePurpose = "сброса пароля",
        )
    }

    private fun sendCode(
        toAddress: String,
        plainCode: String,
        subject: String,
        codePurpose: String,
        ignoreHint: String = "Если вы не запрашивали эту операцию, проигнорируйте это письмо.",
    ) {
        val mailSender = mailSenderProvider.getIfAvailable()
            ?: throw IllegalArgumentException("Почта не настроена: задайте spring.mail.host и учётные данные SMTP")
        val from = props.fromAddress.ifBlank {
            throw IllegalArgumentException("Задайте project-d.invite-mail.from-address")
        }
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, StandardCharsets.UTF_8.name())
        helper.setFrom(from)
        helper.setTo(toAddress)
        helper.setSubject(subject)
        helper.setText(
            """
            Ваш код подтверждения $codePurpose: $plainCode

            Код действителен 15 минут. $ignoreHint
            """.trimIndent(),
            false,
        )
        mailSender.send(message)
    }
}
