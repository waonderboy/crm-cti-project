package com.brogs.crm.infra.agentaccount.agentprofile;

import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfileDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AgentProfileDaoImpl implements AgentProfileDao {
    private final AgentProfileRepository agentProfileRepository;

    @Override
    public AgentProfile save(AgentProfile agentProfile) {
        return agentProfileRepository.save(agentProfile);
    }

    @Override
    public Optional<AgentProfile> findByEmail(String email) {
        return agentProfileRepository.findByEmail(email);
    }

    @Override
    public Page<AgentProfile> findByAgentAccountId(Long agentAccountId, Pageable pageable) {
        return agentProfileRepository.findByAgentAccountId(agentAccountId, pageable);
    }
}
