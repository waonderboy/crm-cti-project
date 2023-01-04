package com.brogs.crm.domain.company.department;

import com.brogs.crm.domain.AbstractEntity;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import com.brogs.crm.domain.company.Company;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity @Getter
public class Department extends AbstractEntity {

    @Id @GeneratedValue
    @Column(name = "department_id")
    private Long id;
    private String name;
    private String leader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private Set<AgentProfile> agentProfiles;

    @Builder
    public Department(String name,
                 String leader,
                 Company company) {
        this.name = name;
        this.leader = leader;
        this.company = company;
        this.agentProfiles = new HashSet<>();
    }


}
