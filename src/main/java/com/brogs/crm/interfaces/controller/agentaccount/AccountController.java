package com.brogs.crm.interfaces.controller.agentaccount;

import com.brogs.crm.application.AccountFacade;
import com.brogs.crm.common.AccountPrincipal;
import com.brogs.crm.common.response.CommonResponse;
import com.brogs.crm.common.security.CurrentAccount;
import com.brogs.crm.domain.agentaccount.AccountInfo;
import com.brogs.crm.domain.agentaccount.AccountService;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountFacade accountFacade;
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

        var result = accountService.login(accountDtoMapper.of(request));
        return CommonResponse.success(result);
    }

    @GetMapping("/refresh-token")
    public CommonResponse refreshToken(@RequestAttribute @Nullable String refreshToken,
                                       @RequestAttribute @Nullable String subject) {

        var renewTokens = accountService.refreshAccessToken(subject, refreshToken);
        return CommonResponse.success(renewTokens);
    }

    @GetMapping("/me")
    public CommonResponse getAccountInfo(@CurrentAccount AccountPrincipal accountPrincipal) {
        var response = accountDtoMapper.of(accountFacade.getAccountInfo(accountPrincipal.getIdentifier()));
        return CommonResponse.success(response);
    }

    @PostMapping("/profile")
    public CommonResponse settingProfile(@CurrentAccount AccountPrincipal accountPrincipal,
                                         @RequestBody AccountDto.ProfileReq request) {
        log.info("유저 인증 정보 accountPrincipal={}", accountPrincipal.getIdentifier());
        log.info("유저 인증 정보 accountPrincipal={}", accountPrincipal.isHasProfile());
        var profileInfo = accountFacade.registerProfile(accountPrincipal.getIdentifier(), accountDtoMapper.of(request));
        var response = accountDtoMapper.of(profileInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/check-confirm-token")
    public CommonResponse checkConfirmToken(@RequestBody AccountDto.ConfirmTokenReq request) {
        accountFacade.activateProfile(request.getEmail(), request.getToken());
        return CommonResponse.success("계정이 활성화 되었습니다.");
    }
}
