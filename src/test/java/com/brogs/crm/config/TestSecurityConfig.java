package com.brogs.crm.config;

import com.brogs.crm.common.security.SecurityConfig;
import com.brogs.crm.common.security.jwt.JwtExceptionHandlerFilter;
import com.brogs.crm.common.security.jwt.JwtTokenProvider;
import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.agentaccount.AccountInfo;
import com.brogs.crm.domain.agentaccount.AccountService;
import com.brogs.crm.fixture.TestInfoFixture;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.web.filter.CorsFilter;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
    @MockBean JwtTokenProvider tokenProvider;
    @MockBean CorsFilter corsFilter;

}
