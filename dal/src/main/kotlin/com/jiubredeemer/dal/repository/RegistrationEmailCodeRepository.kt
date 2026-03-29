package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entity.RegistrationEmailCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegistrationEmailCodeRepository : JpaRepository<RegistrationEmailCode, String>
