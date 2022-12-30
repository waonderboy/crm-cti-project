package com.brogs.crm.domain.ticketsummary;

import com.brogs.crm.domain.AbstractEntity;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity @Getter
public class TicketSummaryComment extends AbstractEntity {

    @Id @GeneratedValue
    @Column(name = "ticket_summary_comment_id")
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_info_id")
    private AgentProfile agentProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_summary_id")
    private TicketSummary ticketSummary;

    @Builder
    public TicketSummaryComment(String content, AgentProfile agentProfile, TicketSummary ticketSummary) {
        this.content = content;
        this.agentProfile = agentProfile;
        this.ticketSummary = ticketSummary;
    }

}
