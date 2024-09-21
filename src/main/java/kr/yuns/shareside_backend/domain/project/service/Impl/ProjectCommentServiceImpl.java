package kr.yuns.shareside_backend.domain.project.service.Impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.yuns.shareside_backend.domain.auth.data.entity.User;
import kr.yuns.shareside_backend.domain.project.data.dao.ProjectDao;
import kr.yuns.shareside_backend.domain.project.data.dto.request.UploadCommentsRequestDto;
import kr.yuns.shareside_backend.domain.project.data.entity.ProjectsComment;
import kr.yuns.shareside_backend.domain.project.data.entity.ProjectsCommentReply;
import kr.yuns.shareside_backend.domain.project.data.entity.SideProjects;
import kr.yuns.shareside_backend.domain.project.data.repository.ProjectsCommentReplyRepository;
import kr.yuns.shareside_backend.domain.project.data.repository.ProjectsCommentRepository;
import kr.yuns.shareside_backend.domain.project.exception.ProjectNotFoundException;
import kr.yuns.shareside_backend.domain.project.service.ProjectCommentService;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectCommentServiceImpl implements ProjectCommentService {
    private final ProjectsCommentRepository projectsCommentRepository;
    private final ProjectsCommentReplyRepository projectsCommentReplyRepository;
    private final ProjectDao projectDao;
    
    @Override
    public void doUnuseableAllCommentsIncludeReplies(Long projectId) {
        log.info("Start working to disable all comments on targeted side projects");

        log.info("Find target project");
        SideProjects targetProject = projectDao.getSideProjectsEntityViaId(projectId);

        log.info("Find all comments by project");
        List<ProjectsComment> projectsCommentList = projectsCommentRepository.findAllProjectsCommentBySideProjects(targetProject);
        log.info("Found {} comments", projectsCommentList.size());

        LocalDateTime CURRENT_TIME = LocalDateTime.now();

        if(projectsCommentList.size() > 0) {
            log.info("Unuseable {} comments", projectsCommentList.size());
            for(ProjectsComment projectsComment : projectsCommentList) {
                projectsComment.setUseable(false);
                projectsComment.setUpdatedAt(CURRENT_TIME);
                projectsCommentRepository.save(projectsComment);

                log.info("Find a reply to this comment");
                List<ProjectsCommentReply> projectsCommentReplyList = projectsCommentReplyRepository.findAllProjectsCommentReplyByProjectsComment(projectsComment);
                log.info("Found {} replies", projectsCommentReplyList.size());

                if(projectsCommentReplyList.size() > 0) {
                    for(ProjectsCommentReply projectsCommentReply : projectsCommentReplyList) {
                        projectsCommentReply.setUseable(false);
                        projectsCommentReply.setUpdatedAt(CURRENT_TIME);
                        projectsCommentReplyRepository.save(projectsCommentReply);
                    }
                }
            }
        }
    }

    @Override
    public ResponseEntity<?> saveComments(User user, UploadCommentsRequestDto uploadCommentsRequestDto) {
        try {
            SideProjects sideProjects = projectDao.getSideProjectsEntityViaId(uploadCommentsRequestDto.getProjectId());

            // Check comments length
            if(uploadCommentsRequestDto.getComment().length() >= 5 && uploadCommentsRequestDto.getComment().length() <= 1000) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("5자 이상, 1,000자 미만의 댓글만 업로드할 수 있습니다.");
            }

            // Check bad words

            LocalDateTime CURRENT_TIME = LocalDateTime.now();

            projectsCommentRepository.save(
                ProjectsComment.builder()
                    .comment(uploadCommentsRequestDto.getComment())
                    .sideProjects(sideProjects)
                    .user(user)
                    .isServiceUser(uploadCommentsRequestDto.isServiceUser())
                    .useable(true)
                    .uploadedAt(CURRENT_TIME)
                    .updatedAt(CURRENT_TIME)
            .build());

            return ResponseEntity.status(HttpStatus.OK).body("댓글이 업로드 되었습니다.");
        } catch (ProjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
}
