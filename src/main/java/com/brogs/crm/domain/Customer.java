package com.brogs.crm.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity @Getter
public class Customer extends AbstractEntity{

    @Id @GeneratedValue
    @Column(name = "customer_id")
    private Long id;
    private String name;
    private String address;
    private String memo;
    private String email;
    private String phoneNumber;
    private String facebookId;
    private String instagramId;
    private String kakaoTalkId;
    private boolean emailReception;
    private boolean smsReception;
    private boolean callReception;
    private boolean facebookReception;
    private boolean instagramReception;
    private boolean kakaoTalkReception;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_info_id")
    private AgentInfo agentInfos;

    @JoinTable(
            name = "customer_hashtag",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    @ManyToMany
    private Set<Hashtag> hashtags;

    @Builder
    public Customer(String name, String email, String phoneNumber, String facebookId, String instagramId, String kakaoTalkId) {
        //TODO: 예외처리
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.facebookId = facebookId;
        this.instagramId = instagramId;
        this.kakaoTalkId = kakaoTalkId;
        this.hashtags = new LinkedHashSet<>();
        defaultReceptionSettings();
    }
    public void defaultReceptionSettings() {
        this.emailReception = true;
        this.smsReception = true;
        this.callReception = true;
        this.facebookReception = true;
        this.instagramReception = true;
        this.kakaoTalkReception = true;
    }
}
