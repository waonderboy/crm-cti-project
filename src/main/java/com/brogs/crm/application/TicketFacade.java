package com.brogs.crm.application;

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
    private final TicketService ticketService;
    // private final AlarmService alarmService; 티켓이 생성되면 담당자에게 알려야함

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
}
