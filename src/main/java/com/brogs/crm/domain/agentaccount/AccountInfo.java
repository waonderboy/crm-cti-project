package com.brogs.crm.domain.agentaccount;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class AccountInfo {

    @Getter
    @Builder
    @ToString
    public static class Register {
        private String identifier;
        private String password;
        private String role;

//        public AccountInfo.Register fromEntity(AgentAccount agentAccount) {
//            return agentAccount
//
//        }
    }
}
