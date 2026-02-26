package com.jiubredeemer.app.integration.charactersheet.dto.character

import com.jiubredeemer.dal.entity.RoomUser
import java.util.*

class FindCharacterByUserIdAndRoomIdRequest (
    val roomId: UUID,
    val userId: UUID,
    val roles: List<RoomUser.Role>?
)

