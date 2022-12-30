package com.brogs.crm.infra.agentaccount.agentprofile;

import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfileDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
