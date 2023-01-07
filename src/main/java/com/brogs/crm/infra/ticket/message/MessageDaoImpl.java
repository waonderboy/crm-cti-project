package com.brogs.crm.infra.ticket.message;

import com.brogs.crm.domain.ticket.message.Message;
import com.brogs.crm.domain.ticket.message.MessageDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageDaoImpl implements MessageDao {
    private final MessageRepository messageRepository;
    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
