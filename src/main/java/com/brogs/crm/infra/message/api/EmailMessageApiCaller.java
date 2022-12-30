package com.brogs.crm.infra.message.api;

import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.externalmessenger.SendMethod;

public class EmailMessageApiCaller implements MessageApiCaller{
    @Override
    public boolean support(SendMethod sendMethod) {
        return SendMethod.EMAIL == sendMethod;
    }

    @Override
    public void sendConfirmMail(AccountCommand.RegisterProfile request, String confirmToken) {
        // TODO: 자바메일 센더로 페이지를 만들어서 보내야함!!
        // request.getEmail(), confirmToken
    }
}
