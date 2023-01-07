package com.brogs.crm.infra.messagebroker;

import com.brogs.crm.domain.messagesystem.producer.MessageProducer;
import com.brogs.crm.domain.ticket.TicketCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProducerImpl implements MessageProducer {
    //TODO: 카프카 메세지 프로듀서로 변경가능
    private final SimpMessagingTemplate template;


    /**
     * 내부 프로듀서로 전송
     */
    @Override
    public void sendMessageToTicket(Long ticketId, TicketCommand.MatchMessage matchMessage) {
        log.info("/sub/chat/ticket/" + ticketId + " 로 메세지 발송");
        log.info("MessageSender = " + matchMessage.getSender() + "의 메세지 = "+ matchMessage.getContent());
        template.convertAndSend("/sub/chat/ticket" + ticketId, matchMessage);
    }

    @Override
    public void sendMessageToCustomerMessenger(TicketCommand.MatchMessage matchMessage) {
        log.info("/sub/chat/question 로 메세지 발송");
        template.convertAndSend("/sub/chat/question", matchMessage);
    }
}
