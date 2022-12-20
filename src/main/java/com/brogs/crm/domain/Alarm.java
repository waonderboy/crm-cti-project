package com.brogs.crm.domain;

import com.brogs.crm.domain.constant.ReadStatusType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity @Getter
public class Alarm {

    @Id @GeneratedValue
    @Column(name = "alarm_id")
    private Long id;
    private String targetId;
    private String content;
    private ReadStatusType readStatus;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agent_account_id")
    private AgentAccount agentAccount;


    private Alarm(String targetId, String content, ReadStatusType readStatus) {
        this.targetId = targetId;
        this.content = content;
        this.readStatus = readStatus;
        this.createdAt = LocalDateTime.now();
    }

    public static Alarm of(String targetId,
                           String content,
                           ReadStatusType readStatus) {
        return new Alarm(targetId, content, readStatus);
    }
}
