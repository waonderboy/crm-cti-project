package com.brogs.crm.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReadStatusType {
    READ("읽음", true), UNREAD("읽지않음", false);
    private final String description;
    private final boolean read;
}
