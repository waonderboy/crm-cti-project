package com.brogs.crm.interfaces.controller.agentaccount;

import com.brogs.crm.common.response.CommonResponse;
import com.brogs.crm.domain.agentaccount.AccountInfo;
import com.brogs.crm.domain.agentaccount.AccountService;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountDtoMapper accountDtoMapper;

    @PostMapping("/join")
    public CommonResponse signUp(@RequestBody AccountDto.SignUpReq request) {

        var registerInfo = accountService.register(accountDtoMapper.of(request));
        var response = accountDtoMapper.of(registerInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/login")
    public CommonResponse signIn(@RequestBody AccountDto.SignInReq request) {

        var tokens = accountService.login(accountDtoMapper.of(request));
        return CommonResponse.success(tokens);
    }

    @GetMapping("/refresh-token")
    public CommonResponse refreshToken(@RequestAttribute @Nullable String refreshToken,
                                       @RequestAttribute @Nullable String subject) {

        var renewTokens = accountService.refreshAccessToken(subject, refreshToken);
        return CommonResponse.success(renewTokens);
    }

}
