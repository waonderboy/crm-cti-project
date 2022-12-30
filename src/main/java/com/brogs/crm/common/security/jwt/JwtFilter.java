package com.brogs.crm.common.security.jwt;

import com.brogs.crm.common.exception.ExpiredCredentialsException;
import com.brogs.crm.common.exception.InvalidCredentialsException;
import com.brogs.crm.common.response.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    private static final String REFRESH_TOKEN_URI = "refresh-token";
    private static final String BEARER = "Bearer ";
    public final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveToken(request, AUTHORIZATION_HEADER);

        try {

            if (isValidToken(accessToken)) {
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("Security Context 에 {} 인증 정보를 저장했습니다", authentication.getName());
            }

        } catch (ExpiredJwtException ex) {

            String requestUri = request.getRequestURI();
            String refreshToken = resolveToken(request, REFRESH_TOKEN_HEADER);
            log.info("토큰 갱신 요청으로 인해 토큰 검증을 시작합니다. requestUri={}", requestUri);

            try {
                if (isValidToken(refreshToken) && requestUri.contains(REFRESH_TOKEN_URI)) {
                    log.info("리프레시 요청");
                    allowForRefreshToken(refreshToken, ex.getClaims().getSubject(), request);
                }
            } catch (ExpiredJwtException e) {
                log.info("리프레시 토큰 만료");
                throw new ExpiredCredentialsException("다시 로그인 해 주세요.", ErrorCode.EXPIRED_REFRESH_TOKEN);
            }
            
        }

        log.info("next filter start");
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request, String header) {
        String rawToken = request.getHeader(header);
        if (StringUtils.hasText(rawToken)) { return getBearerToken(rawToken); }
        if (header == AUTHORIZATION_HEADER) { return null; }

        throw new InvalidCredentialsException("액세스 토큰이 만료되었습니다.", ErrorCode.EXPIRED_ACCESS_TOKEN);
    }

    private String getBearerToken(String bearerToken) {
        if (!bearerToken.startsWith(BEARER)) {
            throw new InvalidCredentialsException("유효한 토큰이 아닙니다.", ErrorCode.INVALID_CREDENTIALS);
        }
        return bearerToken.substring(7);
    }

    private boolean isValidToken(String jwtToken) {
        return  StringUtils.hasText(jwtToken) && tokenProvider.validateToken(jwtToken);
    }

    private void allowForRefreshToken(String refreshToken, String subject, HttpServletRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.setAttribute("refreshToken", refreshToken);
        request.setAttribute("subject", subject);
    }
}
