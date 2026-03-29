package com.jiubredeemer.app.invite.mail

import org.springframework.beans.factory.ObjectProvider
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class RoomInviteEmailService(
    private val mailSenderProvider: ObjectProvider<JavaMailSender>,
    private val props: InviteMailProperties,
) {
    fun sendRoomInviteEmail(toAddress: String, inviteToken: String, roomName: String) {
        val mailSender = mailSenderProvider.getIfAvailable()
            ?: throw IllegalArgumentException("Почта не настроена: задайте spring.mail.host и учётные данные SMTP")
        val from = props.fromAddress.ifBlank {
            throw IllegalArgumentException("Задайте project-d.invite-mail.from-address (адрес отправителя)")
        }
        val base = props.registrationBaseUrl.trimEnd('/')
        if (base.isEmpty()) {
            throw IllegalArgumentException("Задайте project-d.invite-mail.registration-base-url (базовый URL фронтенда)")
        }
        val encodedToken = URLEncoder.encode(inviteToken, StandardCharsets.UTF_8)
        val link = "$base/register?roomInviteToken=$encodedToken"
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, StandardCharsets.UTF_8.name())
        helper.setFrom(from)
        helper.setTo(toAddress)
        helper.setSubject("Приглашение в комнату ProjectD")
        helper.setText(
            """
            Вас пригласили в комнату «$roomName».

            Зарегистрируйтесь по ссылке:
            $link

            После регистрации вы автоматически попадёте в комнату.
            """.trimIndent(),
            false,
        )
        mailSender.send(message)
    }
}
