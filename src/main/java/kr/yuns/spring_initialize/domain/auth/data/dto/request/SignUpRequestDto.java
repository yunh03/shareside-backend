package kr.yuns.spring_initialize.domain.auth.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "요청: 회원가입")
public class SignUpRequestDto {
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 입니다.")
    @Schema(description = "사용자 이메일", example = "example@example.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    @Schema(description = "사용자 비밀번호", example = "********")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 입니다.")
    @Size(min = 8, max = 30, message = "닉네임은 2자 이상 30자 이하로 입력해주세요.")
    @Schema(description = "사용자 닉네임")
    private String nickname;
}
