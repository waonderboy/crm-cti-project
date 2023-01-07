package com.brogs.crm.domain.ticket;

import com.brogs.crm.common.exception.InvalidParamException;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfileDao;
import com.brogs.crm.domain.customer.Customer;
import com.brogs.crm.domain.customer.CustomerDao;
import com.brogs.crm.domain.ticket.message.MessageDao;
import com.brogs.crm.interfaces.controller.ticket.TicketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketService {

    private final TicketDao ticketDao;
    private final AgentProfileDao agentProfileDao;
    private final CustomerDao customerDao;
    private final MessageDao messageDao;

    @Transactional
    public void register(String email, TicketCommand.RegisterTicket registerTicket) {
        var agentProfile = agentProfileDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 프로필입니다."));
        var initTicket = registerTicket.toEntity();
        initTicket.assignedBy(agentProfile);

        ticketDao.save(initTicket);
    }

    public TicketInfo.Main getTicket(Long ticketId) {
        var ticket = ticketDao.findById(ticketId)
                .orElseThrow(() -> new InvalidParamException("티켓이 존재하지 않습니다."));
        return TicketInfo.Main.from(ticket, ticket.getAgentProfile(), ticket.getCustomer());
    }

    @Transactional
    public void registerCustomerAtTicket(Long ticketId, TicketCommand.UpdateCustomer registerCustomer) {
        var ticket = findTicket(ticketId);
        customerDao.findBySnsId(registerCustomer.getSnsId()).ifPresent(e -> {
            throw new InvalidParamException(e.getSnsId() + "는 이미 존재하는 고객 아이디입니다.");
        });

        var customer = customerDao.save(registerCustomer.toEntity());

        ticket.askedBy(customer);
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

    @Transactional
    public Long getTicketIdFromSender(String sender, String channel) {
        var customer = getCustomer(sender, Customer.SnsType.valueOf(channel));
        var ticket = getTicket(customer);
        return ticket.getId();
    }

    /**
     * 고객 및 티켓 조회, 없으면 생성
     */
    @Transactional
    public Customer getCustomer(String sender, Customer.SnsType channel) {
        var bySnsIdAndSnsType = customerDao.findBySnsIdAndSnsType(sender, channel);
        if (bySnsIdAndSnsType.isEmpty()) {
            return customerDao.save(makeCustomer(sender, channel));
        }
        return bySnsIdAndSnsType.get();
    }
    @Transactional
    public Ticket getTicket(Customer customer) {
        var byCustomer_id = ticketDao.findByCustomer_id(customer.getId());
        if (byCustomer_id.isEmpty()) {
            Ticket ticket = makeTicket(customer.getSnsId(), customer.getSnsType().toString());
            ticket.askedBy(customer);
            return ticketDao.save(ticket);
        }
        return byCustomer_id.get();
    }


    private Customer makeCustomer(String sender, Customer.SnsType channel) {
        return Customer.builder()
                .snsId(sender)
                .snsType(channel)
                .build();
    }

    private Ticket makeTicket(String sender, String channel) {
        return Ticket.builder()
                .title(makeTicketTitle(sender, channel))
                .build();
    }

    private String makeTicketTitle(String sender, String channel) {
        var title = new StringBuilder();
        title.append(sender);
        title.append("님이 ");
        title.append(channel);
        title.append("을 통해 문의한 티켓입니다.");
        return title.toString();
    }

    /**
     * 티켓 조회 - 예외 처리
     */
    private Ticket findTicket(Long ticketId) {
        return ticketDao.findById(ticketId).orElseThrow(() -> new UsernameNotFoundException("조회하신 티켓이 없습니다."));
    }
}
