package com.brogs.crm.domain;

import com.brogs.crm.domain.constant.TicketPriorityType;
import com.brogs.crm.domain.constant.TicketStatusType;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity @Getter
public class Ticket extends AbstractEntity {

    @Id @GeneratedValue
    @Column(name = "ticket_id")
    private Long id;
    private String title;
    private String memo;
    private String referenceUrl;
    private int score;
    private TicketStatusType status;
    private TicketPriorityType priority;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_info_id")
    private AgentInfo agentInfo;

//    @OneToMany(mappedBy = "ticket")
//    private List<Message> messages;
//    티켓을 조회하고 -> 여러티켓에서 -> 여러메세지를 조회할 일이 있으면 사용하기 좋다

    @JoinTable(
            name = "ticket_hashtag",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    @ManyToMany
    private Set<Hashtag> hashtags = new LinkedHashSet<>();

    @Builder
    public Ticket(String title, String memo) {
        this.title = title;
        this.memo = memo;
        this.priority = TicketPriorityType.MEDIUM;
        this.status = TicketStatusType.PROCESSING;
    }

}
