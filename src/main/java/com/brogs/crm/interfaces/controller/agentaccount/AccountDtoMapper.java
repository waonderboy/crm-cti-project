package com.brogs.crm.interfaces.controller.agentaccount;

import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.agentaccount.AccountInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AccountDtoMapper {

    AccountCommand.RegisterAccount of(AccountDto.SignUpReq request);
    AccountCommand.login of(AccountDto.SignInReq request);
    AccountDto.SignUpRes of(AccountInfo.Register result);

}