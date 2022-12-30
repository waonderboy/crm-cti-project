package com.brogs.crm.infra.agentaccount.agentprofile;

import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentProfileRepository extends JpaRepository<AgentProfile, Long> {
    AgentProfile save(AgentProfile agentProfile);
    Optional<AgentProfile> findByEmail(String email);
}
