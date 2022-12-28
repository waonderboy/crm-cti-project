package com.brogs.crm.domain.agentaccount;

import java.util.Optional;

public interface AccountDao {
    Optional<AgentAccount> findByIdentifier(String identifier);

    AgentAccount save(AgentAccount agentAccount);

    AgentAccount getByIdentifier(String identifier);
}
