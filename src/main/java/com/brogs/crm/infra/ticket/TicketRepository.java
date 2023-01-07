package com.brogs.crm.infra.ticket;

import com.brogs.crm.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByCustomer_id(Long customerId);

}
