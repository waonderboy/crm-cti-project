package com.brogs.crm.infra.ticket.message;

import com.brogs.crm.domain.ticket.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
