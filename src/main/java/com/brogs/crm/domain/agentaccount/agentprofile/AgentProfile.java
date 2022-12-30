package com.brogs.crm.domain.agentaccount.agentprofile;

import com.brogs.crm.domain.AbstractEntity;
import com.brogs.crm.domain.department.Department;
import com.brogs.crm.domain.Ticket;
import com.brogs.crm.domain.agentaccount.AgentAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private String confirmToken;
    private int age;
    private boolean activated;
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
                     String address,
                     String profileImage) {
        //TODO: 예외처리
        this.name = name;
        this.email = email;
        this.age = age;
        this.rank = rank;
        this.groupName = groupName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.activated = false;
        this.profileImage = profileImage;
    }

    public void activateProfile(boolean activation) {
        this.activated = activation;
    }

    public void matchAccount(AgentAccount agentAccount) {
        this.agentAccount = agentAccount;
        agentAccount.getAgentProfiles().add(this);
    }

    public void generateConfirmToken() {
        this.confirmToken = UUID.randomUUID().toString();
        this.tokenGeneratedAt = LocalDateTime.now();
    }

    public boolean checkConfirmToken(String token) {
        return this.confirmToken.equals(token);
    }

    @Getter
    @AllArgsConstructor
    public enum AgentRankType {
        // example
        MANAGER("팀장"), SUPERVISOR("주임"), STAFF("사원");

        private final String description;
    }

}
