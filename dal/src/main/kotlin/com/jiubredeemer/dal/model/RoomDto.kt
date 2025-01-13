package com.jiubredeemer.dal.model

import java.io.Serializable
import java.sql.Timestamp
import java.util.*

/**
 * DTO for {@link com.jiubredeemer.dal.entities.Room}
 */
data class RoomDto(
    var id: UUID? = null,
    var name: String? = null,
    var description: String? = null,
    var owner: UserDto? = null,
    var createDatetime: Timestamp? = null,
    var updateDatetime: Timestamp? = null,
    var deleteDatetime: Timestamp? = null,
    var lastActivityDatetime: Timestamp? = null
) : Serializable
