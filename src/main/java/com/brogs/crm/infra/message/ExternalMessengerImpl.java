package com.brogs.crm.infra.message;

import com.brogs.crm.common.exception.InvalidParamException;
import com.brogs.crm.domain.agentaccount.AccountCommand;
import com.brogs.crm.domain.externalmessenger.ExternalMessenger;
import com.brogs.crm.infra.message.api.MessageApiCaller;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalMessengerImpl implements ExternalMessenger {

    private final List<MessageApiCaller> messageApiCallerList;

    @Override
    public void sendConfirmMessage(AccountCommand.RegisterProfile request, String confirmToken){
        MessageApiCaller apiCaller = routeApiCaller(request);
        apiCaller.sendConfirmMail(request, confirmToken);
    }

    private MessageApiCaller routeApiCaller(AccountCommand.RegisterProfile request) {
        log.info("요청한 메세지 서비스 체킹");
        log.info("request.getConfirmMessageMethod()={}", request.getConfirmMessageMethod());
        return messageApiCallerList.stream()
                .filter(messageApiCaller -> messageApiCaller.support(request.getConfirmMessageMethod()))
                .findFirst()
                .orElseThrow(InvalidParamException::new);
    }


}
