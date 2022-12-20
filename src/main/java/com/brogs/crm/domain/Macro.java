package com.brogs.crm.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity @Getter
public class Macro {

    @Id @GeneratedValue
    @Column(name = "macro_id")
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_account_id")
    private AgentAccount agentAccount;

    private Macro(String content) {
        this.content = content;
    }

    public static Macro of(String content) {
        return new Macro(content);
    }
}
