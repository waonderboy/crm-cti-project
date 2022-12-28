package com.brogs.crm.domain.agentaccount;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class AccountCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterAccount {
        private final String identifier;
        private final String password;

        public AgentAccount toEntity(String encodedPassword) {
            return AgentAccount.builder()
                    .identifier(identifier)
                    .password(encodedPassword)
                    .build();
        }

    }

    @Getter
    @Builder
    @ToString
    public static class login {
        private final String identifier;
        private final String password;
    }
}
