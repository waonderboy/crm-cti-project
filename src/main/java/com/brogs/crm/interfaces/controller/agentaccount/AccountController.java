package com.brogs.crm.interfaces.controller.agentaccount;

import com.brogs.crm.application.AccountFacade;
import com.brogs.crm.common.AccountPrincipal;
import com.brogs.crm.common.response.CommonResponse;
import com.brogs.crm.common.security.CurrentAccount;
import com.brogs.crm.domain.agentaccount.AccountService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public CommonResponse accountInfo(@CurrentAccount AccountPrincipal accountPrincipal) {
        var response = accountDtoMapper.of(accountFacade.getAccountInfo(accountPrincipal.getIdentifier()));
        return CommonResponse.success(response);
    }

    @GetMapping("/profile")
    public CommonResponse profileList(@CurrentAccount AccountPrincipal accountPrincipal,
                                      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        var response = accountFacade.getAccountProfiles(accountPrincipal.getIdentifier(), pageable)
                .map(accountDtoMapper::of);
        return CommonResponse.success(response);
    }

    @PostMapping("/profile")
    public CommonResponse settingProfile(@CurrentAccount AccountPrincipal accountPrincipal,
                                         @RequestBody AccountDto.ProfileReq request) {
        var profileInfo = accountFacade.registerProfile(accountPrincipal.getIdentifier(), accountDtoMapper.of(request));
        var response = accountDtoMapper.of(profileInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/profile/check-confirm-token")
    public CommonResponse checkConfirmToken(@RequestBody AccountDto.ConfirmTokenReq request) {
        accountFacade.activateProfile(request.getEmail(), request.getToken());
        return CommonResponse.success("프로필이 활성화 되었습니다, 로그인을 다시 해주세요");
    }

    @GetMapping("/profile/elimination")
    public CommonResponse requestDeleteProfile(@CurrentAccount AccountPrincipal accountPrincipal,
                                               @RequestParam String email) {
        accountFacade.requestDeleteProfile(email);
        return CommonResponse.success("삭제 요청이 완료되었습니다.");
    }

}
