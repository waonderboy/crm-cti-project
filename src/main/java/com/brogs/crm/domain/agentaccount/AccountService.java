package com.brogs.crm.domain.agentaccount;

import com.brogs.crm.common.AccountPrincipal;
import com.brogs.crm.common.exception.InvalidParamException;
import com.brogs.crm.common.response.ErrorCode;
import com.brogs.crm.infra.agentaccount.AccountRepository;
import com.brogs.crm.security.jwt.JwtTokenProvider;
import com.brogs.crm.security.jwt.JwtTokens;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService implements UserDetailsService {

    private final AccountDao accountDao;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountInfo.Register register(AccountCommand.RegisterAccount registerAccount) {
        accountDao.findByIdentifier(registerAccount.getIdentifier()).ifPresent(e -> {
            throw new InvalidParamException(ErrorCode.ALREADY_EXISTENT_ACCOUNT);
        });

        AgentAccount initAgentAccount = registerAccount.toEntity(passwordEncoder.encode(registerAccount.getPassword()));

        return AccountInfo.Register.from(accountDao.save(initAgentAccount));
    }

    @Transactional
    public JwtTokens login(AccountCommand.login login) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getIdentifier(), login.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtTokens jwtTokens = tokenProvider.createJwtTokens(authentication);

        return jwtTokens;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        return accountDao.findByIdentifier(identifier)
                .map(AccountInfo.Main::from)
                .map(AccountPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
    }

    public JwtTokens refreshAccessToken(String subject, String refreshToken) {
        String identifier = tokenProvider.getSubject(refreshToken);
        log.info("identifier={}",identifier);

        if (!subject.equals(identifier)) {
            throw new BadCredentialsException("올바른 토큰이 아닙니다.");
        }

        return tokenProvider.renewJwtTokens(refreshToken);
    }
}
