package com.brogs.crm.interfaces.controller;

import com.brogs.crm.common.AccountPrincipal;
import com.brogs.crm.common.response.CommonResponse;
import com.brogs.crm.interfaces.controller.agentaccount.AccountDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public CommonResponse home(@AuthenticationPrincipal AccountPrincipal accountPrincipal) {
        AccountDto.SignUpRes signUpRes = new AccountDto.SignUpRes();
        signUpRes.setIdentifier(accountPrincipal.getIdentifier());
        signUpRes.setRole(accountPrincipal.getAuthorities().toString());
        return CommonResponse.success(signUpRes, "인가된 사용자입니다.");
    }
}
