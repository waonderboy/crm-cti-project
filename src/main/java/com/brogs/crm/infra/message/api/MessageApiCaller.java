package com.brogs.crm.infra.message.api;

import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.externalmessenger.SendMethod;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface MessageApiCaller {
    boolean support(SendMethod sendMethod);
    void sendConfirmMail(AccountCommand.RegisterProfile request, String confirmToken);
}
