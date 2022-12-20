package com.brogs.crm.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity @Getter
public class Group extends AbstractEntity {

    @Id @GeneratedValue
    @Column(name = "group_id")
    private Long id;
    private String name;
    private String leader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private Set<AgentInfo> agentInfos;

    @Builder
    public Group(String name, String leader, Company company) {
        this.name = name;
        this.leader = leader;
        this.company = company;
        this.agentInfos = new HashSet<>();
    }


}
