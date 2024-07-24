package com.jiubredeemer.dal.repository;

import com.jiubredeemer.dal.entities.RoomUser
import com.jiubredeemer.dal.entities.RoomsUserKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomsUserRepository : JpaRepository<RoomUser, RoomsUserKey> {
}
