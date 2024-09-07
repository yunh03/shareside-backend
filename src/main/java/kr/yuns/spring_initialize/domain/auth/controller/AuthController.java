package kr.yuns.spring_initialize.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.yuns.spring_initialize.domain.auth.data.dto.request.SignUpRequestDto;
import kr.yuns.spring_initialize.domain.auth.data.dto.response.AuthResponseDto;

import kr.yuns.spring_initialize.domain.auth.service.AuthService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return authService.signUp(signUpRequestDto);
    }
}
