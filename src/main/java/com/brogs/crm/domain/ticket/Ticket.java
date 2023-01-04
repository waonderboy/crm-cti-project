package com.brogs.crm.domain.ticket;

import com.brogs.crm.domain.AbstractEntity;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import com.brogs.crm.domain.customer.Customer;
import com.brogs.crm.domain.etc.hashtag.Hashtag;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@EqualsAndHashCode(of = "id", callSuper = false)
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_profile_id")
    private AgentProfile agentProfile;

//    @OneToMany(mappedBy = "ticket")
//    private List<Message> messages;
//    티켓을 조회하고 -> 여러티켓에서 -> 여러메세지를 조회할 일이 있으면 사용하기 좋다

    @JoinTable(
            name = "ticket_hashtag",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Hashtag> hashtags = new LinkedHashSet<>();

    @Builder
    public Ticket(String title, String memo, String referenceUrl,
                  TicketPriorityType priority, TicketStatusType status) {
        this.title = title;
        this.memo = memo;
        this.referenceUrl = referenceUrl;
        this.priority = priority;
        this.status = status;
    }

    public void assignedBy(AgentProfile agentProfile) {
        this.agentProfile = agentProfile;
        agentProfile.getTickets().add(this);
    }

    public void askedBy(Customer customer) {
        this.customer= customer;
    }

    @Getter
    @AllArgsConstructor
    public enum TicketPriorityType {

        HIGH("높음"), MEDIUM("보통"), LOW("낮음"), URGENT("긴급");

        private final String description;
    }

    @Getter
    @AllArgsConstructor
    public enum TicketStatusType {

        FINISHED("처리완료"), HOLD("보류"), PROCESSING("처리중"), PENDING("미해결");

        private final String description;
    }
}
