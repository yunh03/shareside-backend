package kr.yuns.spring_initialize.domain.auth.service.Impl;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.yuns.spring_initialize.common.CustomResponseEntity;
import kr.yuns.spring_initialize.common.Result;
import kr.yuns.spring_initialize.config.security.JwtTokenProvider;
import kr.yuns.spring_initialize.domain.auth.data.dto.request.SignUpRequestDto;
import kr.yuns.spring_initialize.domain.auth.data.dto.response.AuthResponseDto;
import kr.yuns.spring_initialize.domain.auth.data.entity.User;
import kr.yuns.spring_initialize.domain.auth.data.repository.UserRepository;
import kr.yuns.spring_initialize.domain.auth.service.AuthService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private JwtTokenProvider tokenProvider;

    @Override
    public ResponseEntity<AuthResponseDto> signUp(SignUpRequestDto signUpRequestDto) {
        if(userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AuthResponseDto.builder()
                            .status(CustomResponseEntity.fail(Result.DUPLICATED_EMAIL))
                    .build());
        }

        if(userRepository.existsByNickname(signUpRequestDto.getNickname())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AuthResponseDto.builder()
                            .status(CustomResponseEntity.fail(Result.DUPLICATED_NICKNAME))
                    .build());
        }

        User user = User.builder()
                        .email(signUpRequestDto.getEmail())
                        .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                        .nickname(signUpRequestDto.getNickname())
                        .roles(Collections.singletonList("ROLE_USER"))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                    .build();

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(AuthResponseDto.builder()
                        .accessToken(tokenProvider.createAccessToken(signUpRequestDto.getEmail(), user.getRoles()))
                        .refreshToken(tokenProvider.createRefreshToken(signUpRequestDto.getEmail()))
                        .status(CustomResponseEntity.success())
                .build());
    }
}