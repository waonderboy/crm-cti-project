package com.brogs.crm.controller;

import com.brogs.crm.common.exception.InvalidParamException;
import com.brogs.crm.common.response.ErrorCode;
import com.brogs.crm.domain.agentaccount.*;
import com.brogs.crm.interfaces.controller.agentaccount.AccountController;
import com.brogs.crm.interfaces.controller.agentaccount.AccountDto;
import com.brogs.crm.common.AccountPrincipal;
import com.brogs.crm.interfaces.controller.agentaccount.AccountDtoMapper;
import com.brogs.crm.security.SecurityConfig;
import com.brogs.crm.security.jwt.JwtTokens;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * BDD 시나리오 + API 스펙 검증
 */
@DisplayName("계정 컨트롤러 - 로그인, 회원가입")
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean AccountDtoMapper accountDtoMapper;
    @MockBean AccountService accountService;

    @DisplayName("[POST] 회원가입 요청 성공")
    @Test
    public void AccountInfo_RequestingSignUp_Success() throws Exception {
        // Given
        given(accountService.register(mock(AccountCommand.RegisterAccount.class))).willReturn(RegisterAccountInfo());
        given(accountDtoMapper.of((AccountInfo.Register) any())).willReturn(SignUpRes());

        // When
        mvc.perform(post("/api/v1/account/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("SUCCESS"))
                .andExpect(jsonPath("data.identifier").exists())
                .andExpect(jsonPath("data.role").exists());

        // Then
        then(accountService).should().register(any());
    }

    @DisplayName("[POST] 회원가입 요청 실패 - 동일한 계정이 존재")
    @Test
    public void AccountInfo_RequestingSignUp_Fail() throws Exception {
        //Given
        given(accountService.register(existentAccount())).willThrow(new InvalidParamException(ErrorCode.ALREADY_EXISTENT_ACCOUNT));
        //given(accountDtoMapper.of((AccountInfo.Register) any())).willReturn(SignUpRes());

        //When
        mvc.perform(post("/api/v1/account/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signUpRequest())))
                .andDo(print())
                .andExpect(status().isOk());

        //Then
        //TODO :: 응답실패 컨트롤러로 보내야함
        //then(accountDtoMapper).shouldHaveNoMoreInteractions();
    }

    @DisplayName("[POST] 로그인 요청 성공")
    @Test
    public void AccountInfo_RequestingSignIn_Success() throws Exception{
        //Given
        given(accountService.login(mock(AccountCommand.login.class))).willReturn(mock(JwtTokens.class));

        //When
        mvc.perform(post("/api/v1/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signInRequest())))
                .andDo(print())
                .andExpect(status().isOk());

        //Then
        then(accountService).should().login(mock(AccountCommand.login.class));
    }

    @DisplayName("[POST] 로그인 요청 실패 - 잘못된 계정정보")
    @Test
    public void AccountInfo_RequestingSignIn_Fail() throws Exception{
        //Given
        given(accountService.register(any())).willThrow(new InvalidParamException("아이디나 비밀번호를 다시 확인해주세요."));

        //When
        mvc.perform(post("/api/v1/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signInRequest())))
                .andDo(print())
                .andExpect(status().isOk());

        //Then
//        then(accountService).shouldHaveNoMoreInteractions();
    }

    /**
     * 회원가입 성공 테스트를 위한 객체
     */
    private AccountDto.SignUpRes SignUpRes() {
        AccountDto.SignUpRes signUpRes = new AccountDto.SignUpRes();
        signUpRes.setIdentifier("test");
        signUpRes.setRole("USER");
        return signUpRes;
    }
    private AccountInfo.Register RegisterAccountInfo() {
        return AccountInfo.Register.from(AgentAccount.builder()
                .identifier("test")
                .password("test")
                .role(AgentAccount.AccountRoleType.USER)
                .build());
    }

    /**
     * 회원가입 실패 테스트를 위한 객체 - 이미 존재하는 계정
     */
    private AccountCommand.RegisterAccount existentAccount() {
        return AccountCommand.RegisterAccount
                .builder()
                .identifier("existentAccount")
                .password("secret")
                .build();
    }


    private AccountDto.SignInReq signInRequest() {
        AccountDto.SignInReq signIn = new AccountDto.SignInReq();
        signIn.setIdentifier("test1234");
        signIn.setPassword("test1234!");
        return signIn;
    }

    private AccountDto.SignUpReq signUpRequest() {
        AccountDto.SignUpReq signUp = new AccountDto.SignUpReq();
        signUp.setIdentifier("test");
        signUp.setPassword("test");
        return signUp;
    }
}
