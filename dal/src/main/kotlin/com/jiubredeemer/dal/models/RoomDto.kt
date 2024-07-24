package com.jiubredeemer.dal.models

import java.io.Serializable
import java.sql.Timestamp
import java.util.*

/**
 * DTO for {@link com.jiubredeemer.dal.entities.Room}
 */
data class RoomDto(
    val id: UUID? = null,
    val name: String? = null,
    val owner: UserDto? = null,
    val createDatetime: Timestamp? = null,
    val updateDatetime: Timestamp? = null,
    val deleteDatetime: Timestamp? = null,
    val lastActivityDatetime: Timestamp? = null
) : Serializable
