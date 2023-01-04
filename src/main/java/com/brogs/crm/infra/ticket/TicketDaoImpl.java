package com.brogs.crm.infra.ticket;

import com.brogs.crm.domain.ticket.Ticket;
import com.brogs.crm.domain.ticket.TicketDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TicketDaoImpl implements TicketDao {

    private final TicketRepository ticketRepository;

    @Override
    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> findById(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }
}
