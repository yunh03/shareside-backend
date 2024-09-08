package kr.yuns.shareside_backend.domain.project.service;

import org.springframework.http.ResponseEntity;

import kr.yuns.shareside_backend.domain.auth.data.entity.User;
import kr.yuns.shareside_backend.domain.project.data.dto.request.SideProjectUploadRequestDto;

public interface ProjectService {
    ResponseEntity<?> uploadProject(User user, SideProjectUploadRequestDto sideProjectUploadRequestDto);
}
