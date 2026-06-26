package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entity.CombatParticipant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CombatParticipantRepository : JpaRepository<CombatParticipant, UUID>
