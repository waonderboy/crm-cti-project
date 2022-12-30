package com.brogs.crm.domain.agentaccount;

import com.brogs.crm.common.AccountPrincipal;
import com.brogs.crm.common.exception.InvalidCredentialsException;
import com.brogs.crm.common.exception.InvalidParamException;
import com.brogs.crm.common.response.ErrorCode;
import com.brogs.crm.common.security.jwt.JwtTokenProvider;
import com.brogs.crm.common.security.jwt.JwtTokens;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfileDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService implements UserDetailsService {
    private final AccountDao accountDao;
    private final AgentProfileDao agentProfileDao;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountInfo.Register register(AccountCommand.RegisterAccount registerAccount) {
        accountDao.findByIdentifier(registerAccount.getIdentifier()).ifPresent(e -> {
            throw new InvalidParamException(ErrorCode.ALREADY_EXISTENT_ACCOUNT);});

        var initAgentAccount = registerAccount.toEntity(passwordEncoder.encode(registerAccount.getPassword()));
        return AccountInfo.Register.from(accountDao.save(initAgentAccount));
    }

    @Transactional
    public AccountInfo.Login login(AccountCommand.login login) {
        var authToken = new UsernamePasswordAuthenticationToken(login.getIdentifier(), login.getPassword());
        var authentication = authenticationManagerBuilder.getObject().authenticate(authToken);
        var principal = (AccountPrincipal) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return AccountInfo.Login.builder()
                .jwtTokens(tokenProvider.createJwtTokens(authentication))
                .hasProfile(principal.isHasProfile())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        return accountDao.findByIdentifier(identifier)
                .map(AccountInfo.Main::from)
                .map(AccountPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
    }

    @Transactional
    public AccountInfo.AgentInfo registerProfile(String identifier, AccountCommand.RegisterProfile registerProfile) {
        var agentAccount = accountDao.findByIdentifier(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("없는 유저입니다."));
        var initAgentProfile = registerProfile.toEntity();
        var agentProfile = agentProfileDao.save(initAgentProfile);

        initAgentProfile.generateConfirmToken();
        agentProfile.matchAccount(agentAccount);
        return AccountInfo.AgentInfo.from(agentProfile);
    }



    public JwtTokens refreshAccessToken(String subject, String refreshToken) {
        var identifier = tokenProvider.getSubject(refreshToken);

        if (!subject.equals(identifier)) {
            throw new InvalidCredentialsException("올바른 토큰이 아닙니다.");
        }
        return tokenProvider.renewJwtTokens(refreshToken);
    }

    public void activateProfile(String email, String token) {
        var agentProfile = agentProfileDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 프로필입니다."));

        validateConfirmToken(token, agentProfile);
        agentProfile.activateProfile(true);
    }

    public void validateConfirmToken(String token, AgentProfile agentProfile) {
        if (!agentProfile.checkConfirmToken(token)
                || LocalDateTime.now().isAfter(agentProfile.getTokenGeneratedAt().plusMinutes(5))) {
            throw new InvalidParamException("프로필 인증 토큰이 유효하지 않습니다");
        }
    }

    // 역참조 스트림으로
    public AccountInfo.Main getAccountInfo(String identifier) {
        var agentAccount = accountDao.findByIdentifier(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("없는 유저입니다."));
        var agentProfile = agentAccount.getAgentProfiles()
                .stream()
                .filter(profile -> profile.isActivated())
                .findFirst()
                .orElse(null);

        return agentProfile != null ? AccountInfo.Main.from(agentAccount, agentProfile)
                : AccountInfo.Main.from(agentAccount);
    }
}
