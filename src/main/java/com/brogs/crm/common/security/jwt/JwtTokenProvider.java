package com.brogs.crm.common.security.jwt;

import com.brogs.crm.common.AccountPrincipal;
import com.brogs.crm.common.exception.InvalidCredentialsException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
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
    private static final String HAS_PROFILE = "hasProfile";
    private static final String EMAIL = "email";
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
        var principal = (AccountPrincipal) authentication.getPrincipal();
        var hasProfile = principal.isHasProfile();
        var email = principal.getEmail();
        var authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        var accessTokenExpirationDate = getAccessTokenExpirationDate(getCurrentTime());
        var accessToken = createToken(
                authentication.getName(),
                hasProfile,
                authorities,
                email,
                accessTokenExpirationDate);

        var refreshTokenExpirationDate = getRefreshTokenExpirationDate(getCurrentTime());
        var refreshToken = createToken(
                authentication.getName(),
                hasProfile,
                authorities,
                email,
                refreshTokenExpirationDate);

        return JwtTokens.of(accessToken, refreshToken, accessTokenExpirationDate, refreshTokenExpirationDate);
    }

    /**
     * AccessToken 은 과 만료된 AccessToken 과 RefreshToken 으로 인해 갱신됨
     */
    public JwtTokens renewJwtTokens(String refreshToken) {
        var claims = parseClaims(refreshToken);
        var accessTokenExpirationDate = getAccessTokenExpirationDate(getCurrentTime());
        var renewedAccessToken = createToken(
                claims.getSubject(),
                (Boolean) claims.get(HAS_PROFILE),
                claims.get(AUTHORITIES_KEY).toString(),
                claims.get(EMAIL).toString(),
                accessTokenExpirationDate);

        var refreshTokenExpirationDate = getRefreshTokenExpirationDate(getCurrentTime());
        var renewedRefreshToken = createToken(
                claims.getSubject(),
                (Boolean) claims.get(HAS_PROFILE),
                claims.get(AUTHORITIES_KEY).toString(),
                claims.get(EMAIL).toString(),
                refreshTokenExpirationDate);

        return JwtTokens.of(renewedAccessToken, renewedRefreshToken, accessTokenExpirationDate, refreshTokenExpirationDate);
    }

    /**
     * AccessToken 을 파싱해서 SecurityContext 에 저장할 Authentication 객체 생성 (세션 관리 필요없음)
     */
    public Authentication getAuthentication(String token) {
        var claims = parseClaims(token);
        var authorities = Arrays.stream(
                        claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(createPrincipal(claims, authorities), token, authorities);
    }

    public boolean validateToken(String token) {
        return !parseClaims(token).isEmpty() ? true : false;

    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * TokenProvider 내부 메서드
     */
    private String createToken(String username, boolean hasProfile, String authorities, String email, Date expirationDate) {
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, authorities)
                .claim(HAS_PROFILE, hasProfile)
                .claim(EMAIL, email)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expirationDate)
                .compact();
    }

    private AccountPrincipal createPrincipal(Claims claims, Set<SimpleGrantedAuthority> authorities) {
        return AccountPrincipal.builder()
                .identifier(claims.getSubject())
                .hasProfile((Boolean) claims.get(HAS_PROFILE))
                .password(null)
                .email(claims.get(EMAIL).toString())
                .authorities(authorities)
                .build();
    }

    private Claims parseClaims(String token) {
        try { return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new InvalidCredentialsException();
        }
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
