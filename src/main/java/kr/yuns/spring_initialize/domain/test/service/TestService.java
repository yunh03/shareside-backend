package kr.yuns.spring_initialize.domain.test.service;

import org.springframework.http.ResponseEntity;

import kr.yuns.spring_initialize.domain.test.data.dto.TestRequestDto;

public interface TestService {
    ResponseEntity<?> saveTestEntity(TestRequestDto testRequestDto);
}
