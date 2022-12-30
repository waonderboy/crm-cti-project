package com.brogs.crm.fixture;

import com.brogs.crm.common.security.jwt.JwtTokens;
import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.agentaccount.AccountInfo;
import com.brogs.crm.domain.agentaccount.AgentAccount;
import com.brogs.crm.domain.externalmessenger.SendMethod;
import com.brogs.crm.interfaces.controller.agentaccount.AccountDto;

import java.util.Date;

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
        setProfileRes.setAge("24");
        setProfileRes.setEmail("test@test.com");
        setProfileRes.setActivated(false);
        return setProfileRes;
    }

    public static JwtTokens createJwtTokens() {
        return JwtTokens.of("aa", "vv", new Date(), new Date());
    }
}
