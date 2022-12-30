package com.brogs.crm.domain.externalmessenger;

import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.agentaccount.AccountInfo;

public interface ExternalMessenger {
    void sendConfirmMessage(AccountCommand.RegisterProfile request, String confirmToken);

}
