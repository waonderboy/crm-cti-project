package com.brogs.crm.application;

import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.agentaccount.AccountInfo;
import com.brogs.crm.domain.agentaccount.AccountService;
import com.brogs.crm.domain.externalmessenger.ExternalMessenger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountFacade {
    private final AccountService accountService;
    private final ExternalMessenger externalMessenger;
    /**
     * 계정 프로필 등록 및 활성화
     */
    public AccountInfo.AgentInfo registerProfile(String identifier, AccountCommand.RegisterProfile registerProfile) {
        var profileInfo = accountService.registerProfile(identifier, registerProfile);
        externalMessenger.sendConfirmMessage(registerProfile, profileInfo.getConfirmToken());
        return profileInfo;
    }

    public void activateProfile(String email, String confirmToken) {
        accountService.activateProfile(email, confirmToken);
    }

    /**
     * 계정 프로필 조회
     */
    public AccountInfo.Main getAccountInfo(String identifier) {
        return accountService.getAccountInfo(identifier);
    }

    /**
     * 계정 프로필 변경 후 이전 프로필 비 활성화
     */
}
