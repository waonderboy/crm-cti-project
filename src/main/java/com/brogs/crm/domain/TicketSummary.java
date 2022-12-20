package com.brogs.crm.domain;

import com.brogs.crm.domain.constant.TicketPriorityType;
import com.brogs.crm.domain.constant.TicketStatusType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;
@EqualsAndHashCode(of = "id")
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
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<TicketSummaryComment> articleComments = new LinkedHashSet<>();

    private TicketSummary(String title, TicketPriorityType priority, TicketStatusType status, int score) {
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.score = score;
    }

    public static TicketSummary of(String title,
                                   TicketPriorityType priority,
                                   TicketStatusType status,
                                   int score) {
        return new TicketSummary(title, priority, status, score);
    }
}
