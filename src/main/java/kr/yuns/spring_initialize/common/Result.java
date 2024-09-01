package kr.yuns.spring_initialize.common;

import lombok.Getter;

@Getter
public enum Result {
    OK(200, "성공"),
    CREATED(201, "생성"),
    FAIL(400, "실패"),
    BAD_REQUEST(400,"잘못된 요청"),
    SOCIAL_LOGIN_FAIL(401, "소셜 로그인 실패"),
    UNAUTHORIZED_USER(403, "권한 없는 사용자"),
    PASSWORD_NOT_MATCH(403, "비밀번호 미일치"),
    DELETED_USER(401, "탈퇴 유저"),
    TOKEN_EXPIRED(401, "토큰 유효 기간 만료"),
    TOKEN_NOTSUPPORTED(401, "지원되지 않는 토큰 형식"),
    TOKEN_INVALID(401, "잘못된 토큰 형식"),
    DUPLICATED_NICKNAME(400, "이미 존재하는 닉네임"),
    DUPLICATED_EMAIL(400, "이미 존재하는 이메일"),
    NOT_FOUND_USER(401, "사용자 미확인");

    private final int code;
    private final String message;

    Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result resolve(int code) {
        for (Result result : values()) {
            if (result.getCode() == code) {
                return result;
            }
        }
        return null;
    }
}
