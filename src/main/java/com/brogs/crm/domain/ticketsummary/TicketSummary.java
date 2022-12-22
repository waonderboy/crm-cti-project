package com.brogs.crm.domain.ticketsummary;

import com.brogs.crm.domain.AbstractEntity;
import com.brogs.crm.domain.ticket.TicketPriorityType;
import com.brogs.crm.domain.ticket.TicketStatusType;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity @Getter
public class TicketSummary extends AbstractEntity {

    @Id @GeneratedValue
    @Column(name = "ticket_summary_id")
    private Long id;
    private String title;
    private int score;
    private TicketStatusType status;
    private TicketPriorityType priority;

    @ToString.Exclude
    //@OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "ticketSummary", cascade = CascadeType.ALL)
    private Set<TicketSummaryComment> ticketSummaryComments = new LinkedHashSet<>();

    @Builder
    public TicketSummary(String title, TicketPriorityType priority, TicketStatusType status, int score) {
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.score = score;
    }

}
