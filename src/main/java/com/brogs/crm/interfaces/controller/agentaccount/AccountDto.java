package com.brogs.crm.interfaces.controller.agentaccount;

import com.brogs.crm.domain.agentaccount.AccountInfo;
import com.brogs.crm.domain.agentaccount.AgentAccount;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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

    @Getter
    @Setter
    public static class ProfileReq {
        private String name;
        private String profileImage;
        private String email;
        private String age;
        private String phoneNumber;
        private String address;
        private String confirmMessageMethod;
        private String groupName;
    }

    @Getter
    @Setter
    public static class ConfirmTokenReq {
        private String email;
        private String token;
    }

    @Getter
    @Setter
    public static class AccountInfoRes {
        private String identifier;
        private String extension;
        private Set<String> authorities;
        private boolean hasProfile;
        private ProfileRes agentInfo;
    }

    @Getter
    @Setter
    public static class ProfileRes {
        private String name;
        private String profileImage;
        private String email;
        private String age;
        private String phoneNumber;
        private String address;
        private String groupName;
        private boolean activated;
    }

}
