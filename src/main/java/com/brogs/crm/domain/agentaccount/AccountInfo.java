package com.brogs.crm.domain.agentaccount;

import com.brogs.crm.common.security.jwt.JwtTokens;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
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
        private boolean hasProfile;
        private AgentInfo agentInfo;

        public static Main from(AgentAccount entity) {
            return  Main.builder()
                    .identifier(entity.getIdentifier())
                    .password(entity.getPassword())
                    .extension(entity.getExtension())
                    .authorities(Set.of(entity.getRole().toString()))
                    .hasProfile(entity.isHasProfile())
                    .build();
        }

        public static Main from(AgentAccount account, AgentProfile profile) {
            return  Main.builder()
                    .identifier(account.getIdentifier())
                    .password(account.getPassword())
                    .extension(account.getExtension())
                    .authorities(Set.of(account.getRole().toString()))
                    .hasProfile(account.isHasProfile())
                    .agentInfo(AgentInfo.from(profile))
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

        public static AccountInfo.Register from(AgentAccount entity) {
            return Register.builder()
                    .identifier(entity.getIdentifier())
                    .password(entity.getPassword())
                    .role(entity.getRole().toString())
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class Login {
        private JwtTokens jwtTokens;
        private boolean hasProfile;
    }

    @Getter
    @Builder
    @ToString
    public static class AgentInfo {
        private String name;
        private String profileImage;
        private String email;
        private String phoneNumber;
        private String address;
        private String groupName;
        private String confirmToken;
        private int age;
        private boolean activated;

        public static AgentInfo from(AgentProfile entity) {
            return AgentInfo.builder()
                    .name(entity.getName())
                    .profileImage(entity.getProfileImage())
                    .email(entity.getEmail())
                    .age(entity.getAge())
                    .phoneNumber(entity.getPhoneNumber())
                    .address(entity.getAddress())
                    .groupName(entity.getGroupName())
                    .confirmToken(entity.getConfirmToken())
                    .activated(entity.isActivated())
                    .build();
        }
    }
}
