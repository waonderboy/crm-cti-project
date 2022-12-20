package com.brogs.crm.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AgentRankType {
    // example
    MANAGER("팀장"), SUPERVISOR("주임"), STAFF("사원");

    private final String description;
}