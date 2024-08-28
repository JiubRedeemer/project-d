package com.jiubredeemer.dal.models

import com.jiubredeemer.dal.entities.RoomUser
import java.io.Serializable
import java.sql.Timestamp
import java.util.*

/**
 * DTO for {@link com.jiubredeemer.dal.entities.RoomsUser}
 */
data class RoomsUserDto(
    val id: RoomsUserKeyDto? = null,
    val user: UserDto? = null,
    val room: RoomDto? = null,
    val createDatetime: Timestamp? = null,
    val roles: MutableList<RoomUser.Role>? = null
) : Serializable {
    /**
     * DTO for {@link com.jiubredeemer.dal.entities.RoomsUserKey}
     */
    data class RoomsUserKeyDto(val userId: UUID? = null, val roomId: UUID? = null) :
        Serializable
}
