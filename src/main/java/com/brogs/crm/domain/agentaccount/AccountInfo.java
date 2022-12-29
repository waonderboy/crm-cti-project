package com.brogs.crm.domain.agentaccount;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class AccountInfo {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private String identifier;
        private String password;
        private String extension;
        private Set<String> authorities;

        public static Main from(AgentAccount entity) {
            return  Main.builder()
                    .identifier(entity.getIdentifier())
                    .password(entity.getPassword())
                    .extension(entity.getExtension())
                    .authorities(Set.of(entity.getRole().toString()))
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class Register {
        private String identifier;
        private String password;
        private String role;

        public static AccountInfo.Register from(AgentAccount agentAccount) {
            return Register.builder()
                    .identifier(agentAccount.getIdentifier())
                    .password(agentAccount.getPassword())
                    .role(agentAccount.getRole().toString())
                    .build();
        }
    }
}
