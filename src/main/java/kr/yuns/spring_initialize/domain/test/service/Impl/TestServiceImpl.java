package kr.yuns.spring_initialize.domain.test.service.Impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.yuns.spring_initialize.domain.test.data.dto.TestRequestDto;
import kr.yuns.spring_initialize.domain.test.data.entity.Test;
import kr.yuns.spring_initialize.domain.test.data.repository.TestRepository;
import kr.yuns.spring_initialize.domain.test.service.TestService;

@Service
public class TestServiceImpl implements TestService {
    private TestRepository testRepository;

    @Override
    public ResponseEntity<?> saveTestEntity(TestRequestDto testRequestDto) {
        Test savedTest = testRepository.save(
            Test.builder()
                .title(testRequestDto.getTitle())
                .contents(testRequestDto.getContent())
            .build()
        );

        return ResponseEntity.status(HttpStatus.OK).body(savedTest.getId());
    }
}
