package com.brogs.crm.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "id")
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

    private Forward(String ticketId, String content, String sender, String senderGroup, String receiver, String receiverGroup) {
        this.ticketId = ticketId;
        this.content = content;
        this.sender = sender;
        this.senderGroup = senderGroup;
        this.receiver = receiver;
        this.receiverGroup = receiverGroup;
    }

    public static Forward of(String ticketId,
                             String content,
                             String sender,
                             String senderGroup,
                             String receiver,
                             String receiverGroup) {
        // 정해진 폼대로 보내야하기 때문에 팩토리 메서드 패턴으로 작
        return new Forward(ticketId, content, sender, senderGroup, receiver, receiverGroup);
    }
}
