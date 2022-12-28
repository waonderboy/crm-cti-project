package com.brogs.crm.infra.agentaccount;

import com.brogs.crm.domain.agentaccount.AgentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AgentAccount, Long> {

    Optional<AgentAccount> findByIdentifier(String identifier);

    AgentAccount save(AgentAccount agentAccount);

    AgentAccount getReferenceByIdentifier(String identifier);

}
