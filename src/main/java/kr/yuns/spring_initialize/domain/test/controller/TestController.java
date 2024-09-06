package kr.yuns.spring_initialize.domain.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.yuns.spring_initialize.domain.test.data.dto.TestRequestDto;
import kr.yuns.spring_initialize.domain.test.service.TestService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/test")
@AllArgsConstructor
public class TestController {
    private TestService testService;

    @GetMapping("/")
    public ResponseEntity<?> test() {
        return ResponseEntity.status(HttpStatus.OK).body("test");
    }

    @PostMapping("/ent")
    public ResponseEntity<?> saveTestEntity(@RequestParam String title, String content) {
        return testService.saveTestEntity(
            TestRequestDto.builder()
                .title(title)
                .content(content)
        .build());
    }
}
