package kr.yuns.shareside_backend.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.yuns.shareside_backend.domain.auth.data.dto.request.SignInRequestDto;
import kr.yuns.shareside_backend.domain.auth.data.dto.request.SignUpRequestDto;
import kr.yuns.shareside_backend.domain.auth.data.dto.response.AuthResponseDto;

import kr.yuns.shareside_backend.domain.auth.service.AuthService;
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

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        return authService.signIn(signInRequestDto);
    }

    @PostMapping("/checkNickNameExist")
    public boolean checkNickNameExist(@RequestParam String nickname) {
        return authService.checkNickNameExist(nickname);
    }

    @PostMapping("/checkEmailExist")
    public boolean checkEmailExist(@RequestParam String email) {
        return authService.checkEmailExist(email);
    }
}
