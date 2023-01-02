package com.brogs.crm.domain.agentaccount;

import com.brogs.crm.domain.AbstractEntity;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity @Getter
public class AgentAccount extends AbstractEntity {

    @Id @GeneratedValue
    @Column(name = "agent_account_id")
    private Long id;
    private String identifier;
    private String password;
    private String extension;
    private boolean hasProfile;
    private LocalDateTime lastExpiredAt;

    @Enumerated(EnumType.STRING)
    private AccountRoleType role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agentAccount") // 관계의 주인을 명시, 주인에서만 수정 삭제 가능
    private Set<AgentProfile> agentProfiles;

    @Builder
    public AgentAccount(String identifier,
                        String password,
                        String extension) {
        //TODO : 예외처리 로직
        this.identifier = identifier;
        this.password = password;
        this.role = AccountRoleType.USER;
        this.extension = extension;
        this.hasProfile = false;
        this.lastExpiredAt = LocalDateTime.now();
        this.agentProfiles = new HashSet<>();
    }

    public void setHasProfile(boolean hasProfile) {
        this.hasProfile = hasProfile;
    }

    @Getter
    @AllArgsConstructor
    public enum AccountRoleType {
        USER("ROLE_USER"), MANAGER("ROLE_MANAGER"), ADMIN("ROLE_ADMIN");

        private final String description;
    }
}
