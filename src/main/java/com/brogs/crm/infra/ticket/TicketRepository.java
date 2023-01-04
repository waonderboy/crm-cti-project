package com.brogs.crm.infra.ticket;

import com.brogs.crm.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
