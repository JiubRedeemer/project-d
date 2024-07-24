package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entities.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomRepository : JpaRepository<Room, UUID> {
}
