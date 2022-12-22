package com.brogs.crm.domain.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TicketPriorityType {

    HIGH("높음"), MEDIUM("보통"), LOW("낮음"), URGENT("긴급");

    private final String description;
}