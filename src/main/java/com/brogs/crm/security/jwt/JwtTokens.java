package com.brogs.crm.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
public class JwtTokens {
    private String AccessToken;
    private String RefreshToken;
    private Date AccessTokenExpiredAt;
    private Date RefreshTokenExpiredAt;

    private JwtTokens(String accessToken, String refreshToken, Date accessTokenExpiredAt, Date refreshTokenExpiredAt) {
        AccessToken = accessToken;
        RefreshToken = refreshToken;
        AccessTokenExpiredAt = accessTokenExpiredAt;
        RefreshTokenExpiredAt = refreshTokenExpiredAt;
    }

    public static JwtTokens of(String accessToken,
                               String refreshToken,
                               Date accessTokenExpiredAt,
                               Date refreshTokenExpiredAt){

        return new JwtTokens(accessToken, refreshToken, accessTokenExpiredAt, refreshTokenExpiredAt);
    }
}
