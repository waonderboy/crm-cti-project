package com.brogs.crm.infra.message.api;

import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.externalmessenger.SendMethod;

public interface MessageApiCaller {
    boolean support(SendMethod sendMethod);
    void sendConfirmMail(AccountCommand.RegisterProfile request, String confirmToken);
}