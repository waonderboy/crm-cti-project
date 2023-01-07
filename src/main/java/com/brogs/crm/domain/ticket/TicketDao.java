package com.brogs.crm.domain.ticket;

import java.util.Optional;

public interface TicketDao {
    Ticket save(Ticket ticket);

    Optional<Ticket> findById(Long ticketId);

    Optional<Ticket> findByCustomer_id(Long customerId);

    Ticket getReferenceById(Long ticketId);
}
