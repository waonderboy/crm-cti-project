package com.brogs.crm.domain;

import jakarta.persistence.*;
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

    private Message(String sender, String content, String attachment, String channel, boolean innerMemo, LocalDateTime sendTime) {
        this.sender = sender;
        this.content = content;
        this.attachment = attachment;
        this.channel = channel;
        this.read = false;
        this.innerMemo = innerMemo;
        this.sendTime = sendTime;
    }

    public static Message of(String sender,
                             String content,
                             String attachment,
                             String channel,
                             boolean innerMemo,
                             LocalDateTime sendTime) {
        return new Message(sender, content, attachment, channel, innerMemo, sendTime);
    }
}
