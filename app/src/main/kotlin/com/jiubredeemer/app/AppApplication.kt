package com.jiubredeemer.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication()
@ComponentScan(basePackages = ["com.jiubredeemer"])
class AppApplication

fun main(args: Array<String>) {
    runApplication<AppApplication>(*args)
}
