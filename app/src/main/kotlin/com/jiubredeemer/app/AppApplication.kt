package com.jiubredeemer.app

import com.jiubredeemer.app.invite.mail.InviteMailProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication()
@ComponentScan(basePackages = ["com.jiubredeemer"])
@EnableConfigurationProperties(InviteMailProperties::class)
class AppApplication

fun main(args: Array<String>) {
    runApplication<AppApplication>(*args)
}
