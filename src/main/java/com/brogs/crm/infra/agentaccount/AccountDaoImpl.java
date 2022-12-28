package com.brogs.crm.infra.agentaccount;

import com.brogs.crm.domain.agentaccount.AccountDao;
import com.brogs.crm.domain.agentaccount.AgentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountDaoImpl implements AccountDao {

    private final AccountRepository accountRepository;

    @Override public Optional<AgentAccount> findByIdentifier(String identifier) { return accountRepository.findByIdentifier(identifier);}
    @Override public AgentAccount save(AgentAccount agentAccount) { return accountRepository.save(agentAccount);}

    @Override public AgentAccount getByIdentifier(String identifier) { return accountRepository.getReferenceByIdentifier(identifier); }
}
