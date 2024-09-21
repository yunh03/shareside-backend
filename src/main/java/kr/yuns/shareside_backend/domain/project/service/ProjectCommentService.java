package kr.yuns.shareside_backend.domain.project.service;

import org.springframework.http.ResponseEntity;

import kr.yuns.shareside_backend.domain.auth.data.entity.User;
import kr.yuns.shareside_backend.domain.project.data.dto.request.UploadCommentsRequestDto;

public interface ProjectCommentService {
    void doUnuseableAllCommentsIncludeReplies(Long projectId);
    ResponseEntity<?> saveComments(User user, UploadCommentsRequestDto uploadCommentsRequestDto);
}
