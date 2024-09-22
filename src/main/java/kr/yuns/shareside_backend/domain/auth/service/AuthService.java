package kr.yuns.shareside_backend.domain.auth.service;

import org.springframework.http.ResponseEntity;

import kr.yuns.shareside_backend.domain.auth.data.dto.request.SignInRequestDto;
import kr.yuns.shareside_backend.domain.auth.data.dto.request.SignUpRequestDto;
import kr.yuns.shareside_backend.domain.auth.data.dto.response.AuthResponseDto;

public interface AuthService {
    ResponseEntity<AuthResponseDto> signUp(SignUpRequestDto signUpRequestDto);
    ResponseEntity<AuthResponseDto> signIn(SignInRequestDto signInRequestDto);
    boolean checkNickNameExist(String nickname);
    boolean checkEmailExist(String email);
}