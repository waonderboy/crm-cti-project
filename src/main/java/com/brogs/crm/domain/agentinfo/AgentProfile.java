package com.brogs.crm.domain.agentinfo;

import com.brogs.crm.domain.AbstractEntity;
import com.brogs.crm.domain.department.Department;
import com.brogs.crm.domain.Ticket;
import com.brogs.crm.domain.agentaccount.AgentAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity @Getter
public class AgentProfile extends AbstractEntity {

    @Id @GeneratedValue
    @Column(name = "agent_profile_id")
    private Long id;
    private String name;
    private String email;
    private String groupName;
    private String phoneNumber;
    private String address;
    private String emailCheckToken;
    private int age;
    private boolean expire;
    private boolean certification;
    private AgentRankType rank;
    private LocalDateTime tokenGeneratedAt;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_account_id")
    private AgentAccount agentAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(mappedBy = "agentProfile")
    private Ticket ticket;

    @Builder
    public AgentProfile(String name,
                     String email,
                     int age,
                     AgentRankType rank,
                     String groupName,
                     String phoneNumber,
                     String address) {
        //TODO: 예외처리
        this.name = name;
        this.email = email;
        this.age = age;
        this.rank = rank;
        this.groupName = groupName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.expire = false;
        this.tokenGeneratedAt = LocalDateTime.now();
        this.certification = false;
    }

}
