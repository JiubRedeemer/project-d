package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entity.PasswordResetEmailCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PasswordResetEmailCodeRepository : JpaRepository<PasswordResetEmailCode, String>
