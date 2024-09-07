package kr.yuns.spring_initialize.domain.auth.data.dto.response;

import kr.yuns.spring_initialize.common.CustomResponseEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private CustomResponseEntity<?> status;
}
