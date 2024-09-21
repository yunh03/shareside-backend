package kr.yuns.shareside_backend.domain.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import kr.yuns.shareside_backend.config.security.JwtTokenProvider;
import kr.yuns.shareside_backend.domain.auth.data.entity.User;
import kr.yuns.shareside_backend.domain.project.data.dto.request.SideProjectUploadRequestDto;
import kr.yuns.shareside_backend.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final JwtTokenProvider jwtTokenProvider;

    public User GET_USER_ENTITY_BY_TOKEN(HttpServletRequest request) {
        String token = jwtTokenProvider.extractToken(request);
        return jwtTokenProvider.getUserEntity(token);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadProject(HttpServletRequest request, @RequestBody SideProjectUploadRequestDto sideProjectUploadRequestDto) {
        return projectService.uploadProject(GET_USER_ENTITY_BY_TOKEN(request), sideProjectUploadRequestDto);
    }
}
