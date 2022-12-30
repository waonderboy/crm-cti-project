package com.brogs.crm.domain.agentaccount;

import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import com.brogs.crm.domain.externalmessenger.SendMethod;
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

    @Getter
    @Builder
    @ToString
    public static class RegisterProfile {
        private String name;
        private String profileImage;
        private String email;
        private int age;
        private String phoneNumber;
        private String address;
        private String groupName;
        private SendMethod confirmMessageMethod;

        public AgentProfile toEntity() {
            return AgentProfile.builder()
                    .name(name)
                    .profileImage(profileImage)
                    .email(email)
                    .age(age)
                    .phoneNumber(phoneNumber)
                    .address(address)
                    .groupName(groupName)
                    .build();
        }
    }
}
