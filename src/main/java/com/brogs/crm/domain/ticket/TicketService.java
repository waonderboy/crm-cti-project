package com.brogs.crm.domain.ticket;

import com.brogs.crm.common.exception.InvalidParamException;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfileDao;
import com.brogs.crm.domain.customer.CustomerDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketService {

    private final TicketDao ticketDao;
    private final AgentProfileDao agentProfileDao;
    private final CustomerDao customerDao;

    @Transactional
    public void register(String email, TicketCommand.RegisterTicket registerTicket) {
        var agentProfile = agentProfileDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 프로필입니다."));
        var initTicket = registerTicket.toEntity();
        initTicket.assignedBy(agentProfile);

        ticketDao.save(initTicket);
    }

    public TicketInfo.Main getTicket(Long ticketId) {
        Ticket ticket = ticketDao.findById(ticketId)
                .orElseThrow(() -> new InvalidParamException("티켓이 존재하지 않습니다."));
        return TicketInfo.Main.from(ticket, ticket.getAgentProfile(), ticket.getCustomer());
    }

    @Transactional
    public void registerCustomerAtTicket(Long ticketId, TicketCommand.UpdateCustomer registerCustomer) {
        var ticket = findTicket(ticketId);
        customerDao.findBySnsId(registerCustomer.getSnsId()).ifPresent(e -> {
            throw new InvalidParamException(e.getSnsId() + "는 이미 존재하는 고객 아이디입니다.");
        });

        var initCustomer = customerDao.save(registerCustomer.toEntity());

        ticket.askedBy(initCustomer);
    }

    @Transactional
    public void updateCustomerAtTicket(Long ticketId, TicketCommand.UpdateCustomer updateCustomer) {
        var ticket = findTicket(ticketId);
        ticket.getCustomer().changeCustomerDetails(
                updateCustomer.getName(),
                updateCustomer.getEmail(),
                updateCustomer.getAddress(),
                updateCustomer.getMemo(),
                updateCustomer.getPhoneNumber(),
                updateCustomer.getSnsId(),
                updateCustomer.getSnsType());
    }

    private Ticket findTicket(Long ticketId) {
        return ticketDao.findById(ticketId).orElseThrow(() -> new UsernameNotFoundException("조회하신 티켓이 없습니다."));
    }
}
