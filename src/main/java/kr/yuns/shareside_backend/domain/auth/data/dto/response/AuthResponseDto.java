package kr.yuns.shareside_backend.domain.auth.data.dto.response;

import kr.yuns.shareside_backend.common.CustomResponseEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private CustomResponseEntity<?> status;
}
