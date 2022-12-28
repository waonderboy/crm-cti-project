package com.brogs.crm.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다."),

    // AgentAccount
    ALREADY_EXISTENT_ACCOUNT("이미 존재하는 계정입니다"),
    INVALID_CREDENTIALS("유효한 인증이 아닙니다."),
    EXPIRED_ACCESS_TOKEN("액세스 토큰이 만료되었습니다."),
    EXPIRED_REFRESH_TOKEN("갱신 토큰이 만료되었습니다.");

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}