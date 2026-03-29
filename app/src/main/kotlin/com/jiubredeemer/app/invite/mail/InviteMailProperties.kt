package com.jiubredeemer.app.invite.mail

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "project-d.invite-mail")
data class InviteMailProperties(
    val fromAddress: String = "",
    val registrationBaseUrl: String = "",
)
