package com.brogs.crm.domain.ticket;

import java.util.Optional;

public interface TicketDao {
    void save(Ticket ticket);

    Optional<Ticket> findById(Long ticketId);
}
