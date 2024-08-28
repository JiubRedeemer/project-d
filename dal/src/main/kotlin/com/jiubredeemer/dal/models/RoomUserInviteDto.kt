package com.jiubredeemer.dal.models

import com.jiubredeemer.dal.entities.RoomUser
import com.jiubredeemer.dal.entities.RoomUserInvite
import java.io.Serializable
import java.sql.Timestamp
import java.util.*

/**
 * DTO for {@link com.jiubredeemer.dal.entities.RoomUserInvite}
 */
data class RoomUserInviteDto(
    var id: UUID? = null,
    var roomId: UUID? = null,
    var roomName: String? = null,
    var roomDescription: String? = null,
    var ownerId: UUID? = null,
    var ownerUsername: String? = null,
    var invitedUserEmail: String? = null,
    var invitedUserId: UUID? = null,
    var createDatetime: Timestamp? = null,
    var role: RoomUser.Role? = null,
    var status: RoomUserInvite.Status? = null
) : Serializable
