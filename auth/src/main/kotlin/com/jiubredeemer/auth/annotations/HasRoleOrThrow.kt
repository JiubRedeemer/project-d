package com.jiubredeemer.auth.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class HasRoleOrThrow(vararg val value: String)
