package com.brogs.crm.interfaces.controller.agentaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class AccountDto {

    @Getter
    @Setter
    public static class SignUpReq {
        private String identifier;
        private String password;
    }

    @Getter
    @Setter
    public static class SignUpRes {
        private String identifier;
        private String role;
    }

    @Getter
    @Setter
    public static class SignInReq {
        private String identifier;
        private String password;
    }

}
