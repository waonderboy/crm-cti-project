package com.brogs.crm.interfaces.controller.agentaccount;

import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.agentaccount.AccountInfo;
import com.brogs.crm.domain.externalmessenger.SendMethod;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AccountDtoMapper {
    // 회원가입 요청 & 응답
    AccountCommand.RegisterAccount of(AccountDto.SignUpReq request);
    AccountDto.SignUpRes of(AccountInfo.Register result);

    // 로그인 요청
    AccountCommand.login of(AccountDto.SignInReq request);

    // 프로필 등록 요청 & 응답
    @Mapping(target = "confirmMessageMethod", source = "confirmMessageMethod" )
    AccountCommand.RegisterProfile of(AccountDto.ProfileReq request);
    AccountDto.ProfileRes of(AccountInfo.AgentInfo result);

    // 계정 정보요청
    AccountDto.AccountInfoRes of(AccountInfo.Main result);

}