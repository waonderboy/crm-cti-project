package com.brogs.crm.interfaces.socket;

import com.brogs.crm.application.TicketFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {
    private final TicketFacade ticketFacade;
    private final MessageDtoMapper messageDtoMapper;


    /**
     * Agent 의 티켓에서 /pub/chat/answer 로 발송하는 메세지를 핸들링함
     */
    @MessageMapping("/chat/answer")
    public void message(MessageDto.Main message){
        log.info("messageSender = [ {} ] , Message Content = [ {} ]", message.getSender(), message.getContent());
        ticketFacade.serveMessageToCustomer(messageDtoMapper.of(message));
    }


    /**
     * 고객 문의 페이지에서 /pub/chat/question 로 발송하는 메세지를 핸들링함
     */
    @MessageMapping("/chat/question")
    public void questionMessage(MessageDto.Question message){
        log.info("messageSender = [ {} ] , Message Content = [ {} ]", message.getSender(), message.getContent());
        log.info("messageChannel = [ {} ]", message.getChannel());
        ticketFacade.serveMessageToTicket(messageDtoMapper.of(message));
    }




}
