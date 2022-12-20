package com.brogs.crm.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TicketStatusType {

    FINISHED("처리완료"), HOLD("보류"), PROCESSING("처리중"), PENDING("미해결");

    private final String description;
}