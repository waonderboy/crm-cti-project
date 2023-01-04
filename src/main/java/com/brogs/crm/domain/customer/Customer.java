package com.brogs.crm.domain.customer;

import com.brogs.crm.domain.AbstractEntity;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import com.brogs.crm.domain.etc.hashtag.Hashtag;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity @Getter
public class Customer extends AbstractEntity {

    @Id @GeneratedValue
    @Column(name = "customer_id")
    private Long id;
    private String name;
    private String address;
    private String memo;
    private String email;
    private String phoneNumber;
    private String snsId;
    private SnsType snsType;
    private boolean emailReception;
    private boolean smsReception;
    private boolean callReception;
    private boolean snsReception;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_profile_id")
    private AgentProfile agentProfile;

    @JoinTable(
            name = "customer_hashtag",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    @ManyToMany
    private Set<Hashtag> hashtags;

    @Builder
    public Customer(String name,
                    String email,
                    String address,
                    String memo,
                    String phoneNumber,
                    String snsId,
                    SnsType snsType) {
        //TODO: 예외처리
        this.name = name;
        this.email = email;
        this.address = address;
        this.memo = memo;
        this.phoneNumber = phoneNumber;
        this.snsId = snsId;
        this.snsType = snsType;
        this.hashtags = new LinkedHashSet<>();
        defaultReceptionSettings();
    }
    public void defaultReceptionSettings() {
        this.emailReception = true;
        this.smsReception = true;
        this.callReception = true;
    }
    public void changeCustomerDetails(String name,
                                      String email,
                                      String address,
                                      String memo,
                                      String phoneNumber,
                                      String snsId,
                                      SnsType snsType){
        this.name = name;
        this.email = email;
        this.address = address;
        this.memo = memo;
        this.phoneNumber = phoneNumber;
        this.snsId = snsId;
        this.snsType = snsType;
    }

    public enum SnsType {
        INSTAGRAM, FACEBOOK, KAKAO
    }
}
