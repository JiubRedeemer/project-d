package com.jiubredeemer.app.integration.charactersheet.dto.character

import java.util.*

class FindCharacterByUserIdAndRoomIdRequest (
    val roomId: UUID,
    val userId: UUID
)

