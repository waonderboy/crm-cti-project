package com.brogs.crm.domain.messagesystem.messenger;

import com.brogs.crm.domain.agentaccount.AccountCommand;

public interface ExternalMessenger {
    void sendConfirmMessage(AccountCommand.RegisterProfile request, String confirmToken);

}
