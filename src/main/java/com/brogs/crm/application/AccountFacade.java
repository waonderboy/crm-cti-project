package com.brogs.crm.application;

import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.agentaccount.AccountInfo;
import com.brogs.crm.domain.agentaccount.AccountService;
import com.brogs.crm.domain.externalmessenger.ExternalMessenger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountFacade {
    private final AccountService accountService;
    private final ExternalMessenger externalMessenger;
    /**
     * 계정 프로필 등록 및 인증 메일 발송
     */
    public AccountInfo.AgentInfo registerProfile(String identifier, AccountCommand.RegisterProfile registerProfile) {
        var profileInfo = accountService.registerProfile(identifier, registerProfile);
        externalMessenger.sendConfirmMessage(registerProfile, profileInfo.getConfirmToken());
        return profileInfo;
    }

    /**
     * 프로필 활성화 및 추후 알람 서비스
     */
    public void activateProfile(String email, String confirmToken) {
        accountService.activateProfile(email, confirmToken);
        // TODO : 알람 서비스로 계정에 프로필이 활성화 됬다고 알림 - 재 로그인 필요
    }

    /**
     * 계정 프로필 조회
     */
    public AccountInfo.Main getAccountInfo(String identifier) {
        return accountService.getAccountInfo(identifier);
    }

    /**
     * 계정과 연관된 모든 프로필 조회 (페이징)
     */
    public Page<AccountInfo.AgentInfo> getAccountProfiles(String identifier, Pageable pageable) {
        return accountService.getAccountProfiles(identifier, pageable);
    }

    /**
     * 계정 프로필 연결 해제 요청
     * 부서장 인증 필요 - 알람서비스로 부서장에게 알람 발송
     * 부서장은 프로필 조회에서 삭제 수용
     * (계정 HasProfile 변경, 계정 activated 변경)
     */
    public void requestDeleteProfile(String email) {
        accountService.requestDeleteProfile(email);
        //TODO: 알람서비스로 부서장에게 삭제 요청
        //TODO: 부서장은 프로필 조회에서 삭제 수용
        //AlarmService.NotifyEliminatingProfile()
    }

}
