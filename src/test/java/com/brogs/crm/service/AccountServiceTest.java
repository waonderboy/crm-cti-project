package com.brogs.crm.service;

import com.brogs.crm.domain.agentaccount.AccountDao;
import com.brogs.crm.domain.agentaccount.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@DisplayName("비즈니스 로직 - 계정")
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    // mock 객체를 해당 객체에 주입할 수 있다. System Under Test
    @InjectMocks
    private AccountService sut;

    @Mock
    private AccountDao accountDao;

    @DisplayName("로그인 성공 - Optional 객체 반환")
    @Test
    public void ExistentAccount_SignIn_ReturnOptionalAccount() {
        // Given
        String username = "";
        given(accountDao.findByIdentifier(username)).willReturn(Optional.empty());

        // When
//        sut.login();
    }
}