package kr.yuns.spring_initialize.domain.auth.service;

import org.springframework.http.ResponseEntity;

import kr.yuns.spring_initialize.domain.auth.data.dto.request.SignUpRequestDto;
import kr.yuns.spring_initialize.domain.auth.data.dto.response.AuthResponseDto;

public interface AuthService {
    ResponseEntity<AuthResponseDto> signUp(SignUpRequestDto signUpRequestDto);
}