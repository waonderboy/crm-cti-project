package com.brogs.crm.domain.etc.foward;

import com.brogs.crm.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity @Getter
public class Forward extends AbstractEntity {

    @Id @GeneratedValue
    private Long id;
    private String ticketId;
    private String content;
    private String sender;
    private String senderGroup;
    private String receiver;
    private String receiverGroup;

    @Builder
    public Forward(String ticketId,
                   String content,
                   String sender,
                   String senderGroup,
                   String receiver,
                   String receiverGroup) {
        this.ticketId = ticketId;
        this.content = content;
        this.sender = sender;
        this.senderGroup = senderGroup;
        this.receiver = receiver;
        this.receiverGroup = receiverGroup;
    }
}
