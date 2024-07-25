package com.jiubredeemer.app.rooms.model.response

import java.sql.Timestamp
import java.util.*

data class RoomShortResponse(val id: UUID, val name: String, val lastActivityDate: Timestamp)
