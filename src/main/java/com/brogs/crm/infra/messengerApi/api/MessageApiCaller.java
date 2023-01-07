package com.brogs.crm.infra.messengerApi.api;

import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.messagesystem.messenger.SendMethod;

public interface MessageApiCaller {
    boolean support(SendMethod sendMethod);
    void sendConfirmMail(AccountCommand.RegisterProfile request, String confirmToken);
}
