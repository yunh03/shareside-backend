package kr.yuns.shareside_backend.domain.project.service.Impl;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.yuns.shareside_backend.domain.auth.data.entity.User;
import kr.yuns.shareside_backend.domain.auth.exception.UserNotFoundException;
import kr.yuns.shareside_backend.domain.project.data.dao.ProjectDao;
import kr.yuns.shareside_backend.domain.project.data.dto.request.SideProjectUploadRequestDto;
import kr.yuns.shareside_backend.domain.project.data.entity.SideProjects;
import kr.yuns.shareside_backend.domain.project.data.entity.TechStacks;
import kr.yuns.shareside_backend.domain.project.data.enums.TechStack;
import kr.yuns.shareside_backend.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDao projectDao;

    @Override
    public ResponseEntity<?> uploadProject(User user, SideProjectUploadRequestDto sideProjectUploadRequestDto) {
        try {
            LocalDateTime CURRENT_TIME = LocalDateTime.now();

            log.info(sideProjectUploadRequestDto.toString());

            log.info("save side project entity");
            SideProjects savedSideProjects = projectDao.saveSideProjectEntity(
                SideProjects.builder()
                    .primaryImage(sideProjectUploadRequestDto.getPrimaryImage())
                    .title(sideProjectUploadRequestDto.getTitle())
                    .description(sideProjectUploadRequestDto.getDescription())
                    .contents(sideProjectUploadRequestDto.getContents())
                    .githubUrl(sideProjectUploadRequestDto.getGithubUrl() != null ? sideProjectUploadRequestDto.getGithubUrl() : "미등록")
                    .notionUrl(sideProjectUploadRequestDto.getNotionUrl() != null ? sideProjectUploadRequestDto.getNotionUrl() : "미등록")
                    .deployUrl(sideProjectUploadRequestDto.getDeployUrl() != null ? sideProjectUploadRequestDto.getDeployUrl() : "미등록")
                    .user(user)
                    .category(sideProjectUploadRequestDto.getProjectCategory())
                    .createdAt(CURRENT_TIME)
                    .updatedAt(CURRENT_TIME)
                .build()
            );

            log.info("save tech stack entity");
            if(!sideProjectUploadRequestDto.getTechStacks().isEmpty()) {
                for(TechStack techStack : sideProjectUploadRequestDto.getTechStacks()) {
                    projectDao.saveTechStacksEntity(
                        TechStacks.builder()
                            .techStack(techStack)
                            .sideProjects(savedSideProjects)
                    .build());
                }
            }
        } catch (UserNotFoundException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
