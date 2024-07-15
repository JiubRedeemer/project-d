package com.jiubredeemer.auth.model.request

data class UserRegistration(val username: String, val email: String, val password: String, val matchingPassword: String)
