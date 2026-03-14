package com.jiubredeemer.app.integration.rulebook.dto.room;


import com.jiubredeemer.app.integration.dto.RuleTypeEnum;

import java.util.UUID;

public class RoomDto {
    private UUID roomId;
    private UUID ownerId;
    private RuleTypeEnum ruleType;
    private RuleTypeEnum baseRuleType;

    public RoomDto(UUID roomId, UUID ownerId, RuleTypeEnum ruleType, RuleTypeEnum baseRuleType) {
        this.roomId = roomId;
        this.ownerId = ownerId;
        this.ruleType = ruleType;
        this.baseRuleType = baseRuleType;
    }

    public RoomDto() {
    }

    public UUID getRoomId() {
        return roomId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public RuleTypeEnum getRuleType() {
        return ruleType;
    }

    public RuleTypeEnum getBaseRuleType() {
        return baseRuleType;
    }
}
