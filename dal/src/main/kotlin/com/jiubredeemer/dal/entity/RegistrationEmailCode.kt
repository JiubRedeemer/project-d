package com.jiubredeemer.dal.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Timestamp

@Entity
@Table(name = "registration_email_code", schema = "core")
open class RegistrationEmailCode {

    @Id
    @Column(name = "email", nullable = false)
    open var email: String? = null

    @Column(name = "code_hash", nullable = false)
    open var codeHash: String? = null

    @Column(name = "expires_at", nullable = false)
    open var expiresAt: Timestamp? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Timestamp? = null

    @Column(name = "attempts", nullable = false)
    open var attempts: Int = 0
}
