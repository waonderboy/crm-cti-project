package com.brogs.crm.interfaces.socket;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class MessageDto {

    @Getter
    @Setter
    public static class Main {
        private String sender;
        private String content;
        private String attachment;
        private String channel;
        private boolean receptionStatus;
        private boolean innerMemo;
        private LocalDateTime sendTime;
    }

    @Getter
    @Setter
    public static class Question {
        private String sender;
        private String content;
        private String attachment;
        private String channel;
        private LocalDateTime sendTime;
    }


}
