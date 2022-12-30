package com.brogs.crm.controller;

import com.brogs.crm.WithMockCurrentAccount;
import com.brogs.crm.application.AccountFacade;
import com.brogs.crm.common.exception.InvalidParamException;
import com.brogs.crm.common.response.ErrorCode;
import com.brogs.crm.config.TestSecurityConfig;
import com.brogs.crm.domain.agentaccount.*;
import com.brogs.crm.fixture.TestInfoFixture;
import com.brogs.crm.interfaces.controller.agentaccount.AccountController;
import com.brogs.crm.interfaces.controller.agentaccount.AccountDto;
import com.brogs.crm.interfaces.controller.agentaccount.AccountDtoMapper;
import com.brogs.crm.common.security.jwt.JwtTokens;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.UUID;

import static com.brogs.crm.fixture.TestInfoFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * BDD 시나리오 + API 스펙 검증
 */
@DisplayName("계정 컨트롤러 - 로그인, 회원가입")
@Import(TestSecurityConfig.class)
@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean AccountDtoMapper accountDtoMapper;
    @MockBean AccountService accountService;
    @MockBean AccountFacade accountFacade;

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
                .andExpect(jsonPath("result").value(true))
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
        given(accountService.login(mock(AccountCommand.login.class))).willReturn(any());

        //When
        mvc.perform(post("/api/v1/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signInRequest())))
                .andDo(print())
                .andExpect(status().isOk());

        //Then
        then(accountService).should().login(any());
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
     * 계정 프로필 설정 관련
     */
    @WithMockCurrentAccount(identifier = "test", role = "ROLE_USER")
    @DisplayName("[POST] 계정 프로필 등록 - 성공")
    @Test
    public void AgentProfileInfo_RequestRegistration_ReturnAgentProfileDetails() throws Exception {
        // Given
        given(accountFacade.registerProfile("profile", mock(AccountCommand.RegisterProfile.class)))
                .willReturn(mock((AccountInfo.AgentInfo.class)));
        given(accountDtoMapper.of((AccountInfo.AgentInfo) any())).willReturn(setProfileRes());

        // When
        mvc.perform(post("/api/v1/account/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(setProfileReq())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value(true))
                .andExpect(jsonPath("data.email").exists())
                .andExpect(jsonPath("data.age").exists());

        // Then
        then(accountFacade).should().registerProfile(any(), any());
        then(accountDtoMapper).should().of((AccountDto.ProfileReq) any());
    }

    @DisplayName("[POST] 계정 프로필 활성화 - 성공")
    @Test
    public void ConfirmToken_RequestConfirmProfile_ActivateProfile() throws Exception {
        // Given
        willDoNothing().given(accountFacade).activateProfile(anyString(), anyString());

        // When
        mvc.perform(post("/api/v1/account/check-confirm-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(confirmToken())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value(true));

        // Then
        then(accountFacade).should().activateProfile(anyString(), anyString());
    }


    @WithMockCurrentAccount(identifier = "test", role = "ROLE_USER")
    @DisplayName("[GET] 계정 조회 - 성공")
    @Test
    public void AccountId_RequestAccountInfo_ReturnAccountDetails() throws Exception {
        // Given
        given(accountFacade.getAccountInfo(anyString())).willReturn(mock(AccountInfo.Main.class));

        // When
        mvc.perform(get("/api/v1/account/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value(true))
                .andExpect(jsonPath("data.email").exists());

        // Then
        then(accountFacade).should().getAccountInfo(any());
    }


    private AccountDto.ConfirmTokenReq confirmToken() {
        AccountDto.ConfirmTokenReq confirmTokenReq = new AccountDto.ConfirmTokenReq();
        confirmTokenReq.setEmail("test@test.com");
        confirmTokenReq.setToken(UUID.randomUUID().toString());
        return confirmTokenReq;
    }
}
