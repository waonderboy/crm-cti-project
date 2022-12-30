package com.brogs.crm.infra.message.api;

import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.externalmessenger.SendMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ConsoleMessageApiCaller implements MessageApiCaller{
    @Override
    public boolean support(SendMethod sendMethod) {
        return SendMethod.CONSOLE == sendMethod;
    }

    @Override
    public void sendConfirmMail(AccountCommand.RegisterProfile request, String confirmToken) {
        log.info("Console Message API Called !!");
        log.info("Send Confirm Token[ {} ] to User Email[ {} ]", confirmToken, request.getEmail());

    }
}