package com.brogs.crm.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity @Getter
public class TicketSummaryComment extends AbstractEntity {

    @Id @GeneratedValue
    @Column(name = "ticket_summary_id")
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_info_id")
    private AgentInfo agentInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_summary_id")
    private TicketSummary ticketSummary;

    private TicketSummaryComment(String content, AgentInfo agentInfo, TicketSummary ticketSummary) {
        this.content = content;
        this.agentInfo = agentInfo;
        this.ticketSummary = ticketSummary;
    }

    public static TicketSummaryComment of(String content,
                                   AgentInfo agentInfo,
                                   TicketSummary ticketSummary) {
        return new TicketSummaryComment(content, agentInfo, ticketSummary);
    }
}
