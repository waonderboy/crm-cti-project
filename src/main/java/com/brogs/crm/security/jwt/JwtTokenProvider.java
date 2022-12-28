package com.brogs.crm.security.jwt;

import com.brogs.crm.common.AccountPrincipal;
import com.brogs.crm.common.exception.InvalidCredentialsException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidityInSeconds;
    private final long refreshValidityInSeconds;
    private Key key;

    public JwtTokenProvider(@Value("${JWT_SECRET_KEY}") String secret,
                            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
                            @Value("${jwt.refresh-validity-in-seconds}") long refreshValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
        this.refreshValidityInSeconds = refreshValidityInSeconds;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * authentication 객체를 이용해 최초 로그인시 발급하는 토큰 AccessToken, RefreshToken
     */
    public JwtTokens createJwtTokens(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date accessTokenExpirationDate = getAccessTokenExpirationDate(getCurrentTime());
        String accessToken = createToken(
                authentication.getName(),
                authorities,
                accessTokenExpirationDate);

        Date refreshTokenExpirationDate = getRefreshTokenExpirationDate(getCurrentTime());
        String refreshToken = createToken(
                authentication.getName(),
                authorities,
                refreshTokenExpirationDate);

        return JwtTokens.of(accessToken, refreshToken, accessTokenExpirationDate, refreshTokenExpirationDate);
    }

    /**
     * AccessToken 은 과 만료된 AccessToken 과 RefreshToken 으로 인해 갱신됨
     */
    public JwtTokens renewJwtTokens(String refreshToken) {
        Claims claims = parseClaims(refreshToken);
        Date accessTokenExpirationDate = getAccessTokenExpirationDate(getCurrentTime());
        String renewedAccessToken = createToken(
                claims.getSubject(),
                claims.get(AUTHORITIES_KEY).toString(),
                accessTokenExpirationDate);

        Date refreshTokenExpirationDate = getRefreshTokenExpirationDate(getCurrentTime());
        String renewedRefreshToken = createToken(
                claims.getSubject(),
                claims.get(AUTHORITIES_KEY).toString(),
                refreshTokenExpirationDate);

        return JwtTokens.of(renewedAccessToken, renewedRefreshToken, accessTokenExpirationDate, refreshTokenExpirationDate);
    }

    /**
     * AccessToken 을 파싱해서 SecurityContext 에 저장할 Authentication 객체 생성 (세션 관리 필요없음)
     */
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        AccountPrincipal accountPrincipal = new AccountPrincipal(claims.getSubject(), null, authorities);

        return new UsernamePasswordAuthenticationToken(accountPrincipal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new InvalidCredentialsException();
        }
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * TokenProvider 내부 메서드
     */
    private String createToken(String username, String authorities, Date expirationDate) {
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expirationDate)
                .compact();
    }

    private Claims parseClaims(String token) {
        log.info("parse Token");
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claim;
    }

    private long getCurrentTime() {
        return new Date().getTime();
    }

    private Date getAccessTokenExpirationDate(long now) {
        return new Date(now + tokenValidityInSeconds);
    }

    private Date getRefreshTokenExpirationDate(long now) {
        return new Date(now + refreshValidityInSeconds);
    }
}
