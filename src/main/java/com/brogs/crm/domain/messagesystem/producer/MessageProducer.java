package com.brogs.crm.domain.messagesystem.producer;

import com.brogs.crm.domain.ticket.TicketCommand;

public interface MessageProducer {
    void sendMessageToTicket(Long ticketId, TicketCommand.MatchMessage matchMessage);
    void sendMessageToCustomerMessenger(TicketCommand.MatchMessage matchMessage);
}
