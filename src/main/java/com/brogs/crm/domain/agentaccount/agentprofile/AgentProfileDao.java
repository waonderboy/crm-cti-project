package com.brogs.crm.domain.agentaccount.agentprofile;


import java.util.Optional;

public interface AgentProfileDao {
    AgentProfile save(AgentProfile agentProfile);

    Optional<AgentProfile> findByEmail(String email);
}
