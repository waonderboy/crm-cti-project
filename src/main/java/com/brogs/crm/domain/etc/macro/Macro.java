package com.brogs.crm.domain.etc.macro;

import com.brogs.crm.domain.agentaccount.AgentAccount;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity @Getter
public class Macro {

    @Id @GeneratedValue
    @Column(name = "macro_id")
    private Long id;

    @Setter
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_account_id")
    private AgentAccount agentAccount;


}
