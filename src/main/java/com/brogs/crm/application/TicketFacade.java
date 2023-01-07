package com.brogs.crm.application;

import com.brogs.crm.domain.messagesystem.producer.MessageProducer;
import com.brogs.crm.domain.ticket.TicketCommand;
import com.brogs.crm.domain.ticket.TicketInfo;
import com.brogs.crm.domain.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 트랜잭션 분리를 위한 계층
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TicketFacade {
    // private final AlarmService alarmService;
    // private final AssignTicketSystem assignTicketSystem;
    private final TicketService ticketService;
    private final MessageProducer messageProducer;


    /**
     * 티켓 수동 등록
     */
    public void register(String email, TicketCommand.RegisterTicket registerTicket) {
        ticketService.register(email, registerTicket);
    }

    public TicketInfo.Main getTicket(Long ticketId) {
        return ticketService.getTicket(ticketId);
    }

    public void registerCustomer(Long ticketId, TicketCommand.UpdateCustomer registerCustomer) {
        ticketService.registerCustomerAtTicket(ticketId, registerCustomer);
    }

    public void updateCustomer(Long ticketId, TicketCommand.UpdateCustomer updateCustomer) {
        ticketService.updateCustomerAtTicket(ticketId, updateCustomer);
    }

    /**
     * OSIV False 라도 여기까지 영속성 캐시 적용됨 - osiv의 범위 찾기
     */
    public void serveMessageToTicket(TicketCommand.MatchMessage matchMessage) {
        var sender = matchMessage.getSender();
        var channel = matchMessage.getChannel();
        log.info("channel = {}" , channel);

        var ticketId = ticketService.getTicketIdFromSender(sender, channel);

        messageProducer.sendMessageToTicket(ticketId, matchMessage);
        messageProducer.sendMessageToCustomerMessenger(matchMessage);
//        ticketService.saveMessageInTicket(ticketId, matchMessage);

        // TODO: 2. 티켓 배정 - 배정된 상태면 패스 - try - catch 배당 시간초과같은 예외 발생하면 담당자 없는 미배정 상태로 넘김
            // Long accountId = assignTicketSystem.assignTicketToAccount(미배정 or 배정);
        // TODO: 3. 알람 발행 - 프로듀서로 발행
            // alarmService.alertAccount(accountId);
    }

    public void serveMessageToCustomer(TicketCommand.MatchMessage matchMessage) {
        var sender = matchMessage.getSender();
        var channel = matchMessage.getChannel();

//        var ticketId = ticketService.getTicketIdFromSender(sender, channel)
//                .orElse(ticketService.getTicketIdAfterRegister(sender, channel));

        messageProducer.sendMessageToTicket(1L, matchMessage);
        messageProducer.sendMessageToCustomerMessenger(matchMessage);
//        ticketService.saveMessageInTicket(ticketId, matchMessage);

        // TODO: 2. 티켓 배정 - 배정된 상태면 패스 - try - catch 배당 시간초과같은 예외 발생하면 담당자 없는 미배정 상태로 넘김
        // Long accountId = assignTicketSystem.assignTicketToAccount(미배정 or 배정);
        // TODO: 3. 알람 발행 - 프로듀서로 발행
        // alarmService.alertAccount(accountId);
    }
}
