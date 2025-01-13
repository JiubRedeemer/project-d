package com.jiubredeemer.auth.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class HasRoleOrThrow(vararg val value: String)
