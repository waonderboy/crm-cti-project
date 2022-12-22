package com.brogs.crm.domain.message;

import com.brogs.crm.domain.Ticket;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity @Getter
public class Message {

    @Id @GeneratedValue
    private Long id;
    private String sender;
    private String content;
    private String channel;
    private boolean read;
    private boolean innerMemo;
    private LocalDateTime sendTime;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String attachment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Builder
    public Message(String sender, String content, String attachment, String channel, boolean innerMemo, LocalDateTime sendTime) {
        this.sender = sender;
        this.content = content;
        this.attachment = attachment;
        this.channel = channel;
        this.read = false;
        this.innerMemo = innerMemo;
        this.sendTime = sendTime;
    }

}
