package com.brogs.crm.domain.agentaccount.agentprofile;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AgentProfileDao {
    AgentProfile save(AgentProfile agentProfile);

    Optional<AgentProfile> findByEmail(String email);

    Page<AgentProfile> findByAgentAccountId(Long agentAccountId, Pageable pageable);
}
