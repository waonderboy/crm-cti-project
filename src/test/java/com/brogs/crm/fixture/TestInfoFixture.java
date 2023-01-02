package com.brogs.crm.fixture;

import com.brogs.crm.common.security.jwt.JwtTokens;
import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.agentaccount.AccountInfo;
import com.brogs.crm.domain.agentaccount.AgentAccount;
import com.brogs.crm.domain.externalmessenger.SendMethod;
import com.brogs.crm.interfaces.controller.agentaccount.AccountDto;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class TestInfoFixture {

    public static AccountDto.SignUpRes SignUpRes() {
        AccountDto.SignUpRes signUpRes = new AccountDto.SignUpRes();
        signUpRes.setIdentifier("test");
        signUpRes.setRole("USER");
        return signUpRes;
    }

    public static AccountInfo.Register RegisterAccountInfo() {
        return AccountInfo.Register.from(AgentAccount.builder()
                .identifier("test")
                .password("test")
                .build());
    }

    public static AccountCommand.RegisterAccount existentAccount() {
        return AccountCommand.RegisterAccount
                .builder()
                .identifier("existentAccount")
                .password("secret")
                .build();
    }

    public static AccountCommand.RegisterAccount registerAccount(String identifier) {
        return AccountCommand.RegisterAccount
                .builder()
                .identifier(identifier)
                .password("mockUser")
                .build();
    }

    public static AccountCommand.login loginAccount(String identifier) {
        return AccountCommand.login
                .builder()
                .identifier(identifier)
                .password("mockUser")
                .build();
    }


    public static AccountDto.SignInReq signInRequest() {
        AccountDto.SignInReq signIn = new AccountDto.SignInReq();
        signIn.setIdentifier("test1234");
        signIn.setPassword("test1234!");
        return signIn;
    }

    public static AccountDto.SignUpReq signUpRequest() {
        AccountDto.SignUpReq signUp = new AccountDto.SignUpReq();
        signUp.setIdentifier("test");
        signUp.setPassword("test");
        return signUp;
    }

    public static AccountDto.ProfileReq setProfileReq() {
        AccountDto.ProfileReq setProfileReq = new AccountDto.ProfileReq();
        setProfileReq.setName("test");
        setProfileReq.setAge("24");
        setProfileReq.setEmail("test@test.com");
        setProfileReq.setConfirmMessageMethod("Console");
        return setProfileReq;
    }

    public static AccountDto.ProfileRes setProfileRes() {
        AccountDto.ProfileRes setProfileRes = new AccountDto.ProfileRes();
        setProfileRes.setName("test");
        setProfileRes.setAge(24);
        setProfileRes.setEmail("test@test.com");
        setProfileRes.setActivated(false);
        return setProfileRes;
    }

    /**
     * Agent Profile 확인 토큰 발급
     */
    public static AccountDto.ConfirmTokenReq confirmToken() {
        AccountDto.ConfirmTokenReq confirmTokenReq = new AccountDto.ConfirmTokenReq();
        confirmTokenReq.setEmail("test@test.com");
        confirmTokenReq.setToken(UUID.randomUUID().toString());
        return confirmTokenReq;
    }


    /**
     * [Info 객체] Account 전체 정보 조회 (with Agent profile)
     */
    public static AccountInfo.Main accountWithProfile() {
        return AccountInfo.Main.builder()
                .identifier("test")
                .extension("999-9999")
                .authorities(Set.of("ROLE_USER"))
                .hasProfile(true)
                .agentInfo(agentProfile())
                .build();
    }

    /**
     * [Info 객체] Agent profile 조회
     */
    public static AccountInfo.AgentInfo agentProfile() {
        return AccountInfo.AgentInfo.builder()
                .name("kim")
                .profileImage(null)
                .email("test@test.com")
                .age(24)
                .phoneNumber("010-0000-0000")
                .address(null)
                .groupName("Custom Servbice")
                .confirmToken("asdf")
                .activated(true)
                .build();
    }

    /**
     * [Res 객체] Account 전체 정보 조회 (with Agent profile)
     */
    public static AccountDto.AccountInfoRes accountInfoRes() {
        AccountDto.AccountInfoRes res = new AccountDto.AccountInfoRes();
        res.setIdentifier("test");
        res.setExtension("999-9999");
        res.setAuthorities(Set.of("ROLE_USER"));
        res.setHasProfile(true);
        res.setAgentInfo(profileRes());
        return res;
    }

    /**
     * [Res 객체] Agent profile 조회
     */
    public static AccountDto.ProfileRes profileRes() {
        AccountDto.ProfileRes res = new AccountDto.ProfileRes();
        res.setName("kim");
        res.setProfileImage(null);
        res.setEmail("test@test.com");
        res.setAge(24);
        res.setPhoneNumber("010-0000-0000");
        res.setAddress(null);
        res.setGroupName("Custom Servbice");
        res.setActivated(true);
        return res;
    }
}
