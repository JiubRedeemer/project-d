package com.jiubredeemer.app.joinrequest.model

import java.sql.Timestamp
import java.util.UUID

data class JoinRequestResponse(
    val id: UUID,
    val room: JoinRequestRoomDto,
    val requester: JoinRequestUserDto,
    val status: String,
    val createDatetime: Timestamp
)

data class JoinRequestRoomDto(
    val id: UUID,
    val name: String
)

data class JoinRequestUserDto(
    val id: UUID,
    val username: String
)

data class JoinRequestCountResponse(
    val count: Long
)
