package kr.yuns.shareside_backend.domain.project.data.dao.Impl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import kr.yuns.shareside_backend.domain.project.data.dao.ProjectDao;
import kr.yuns.shareside_backend.domain.project.data.entity.SideProjects;
import kr.yuns.shareside_backend.domain.project.data.entity.TechStacks;
import kr.yuns.shareside_backend.domain.project.data.repository.ProjectsCommentReplyRepository;
import kr.yuns.shareside_backend.domain.project.data.repository.ProjectsCommentRepository;
import kr.yuns.shareside_backend.domain.project.data.repository.ProjectsImagesRepository;
import kr.yuns.shareside_backend.domain.project.data.repository.SideProjectsRepository;
import kr.yuns.shareside_backend.domain.project.data.repository.TechStacksRepository;
import kr.yuns.shareside_backend.domain.project.exception.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectDaoImpl implements ProjectDao {
    private final ProjectsCommentReplyRepository projectsCommentReplyRepository;
    private final ProjectsCommentRepository projectsCommentRepository;
    private final ProjectsImagesRepository projectsImagesRepository;
    private final SideProjectsRepository sideProjectsRepository;
    private final TechStacksRepository techStacksRepository;

    @Override
    public SideProjects saveSideProjectEntity(SideProjects sideProjects) {
        return sideProjectsRepository.save(sideProjects);
    }

    @Override
    public SideProjects getSideProjectsEntityViaId(Long projectId) {
        Optional<SideProjects> sideProjects = sideProjectsRepository.findById(projectId);
        
        if(sideProjects.isPresent()) {
            return sideProjects.get();
        } else {
            throw new ProjectNotFoundException("해당 사이드 프로젝트를 찾을 수 없습니다.");
        }
    }

    @Override
    public void saveTechStacksEntity(TechStacks techStacks) {
        techStacksRepository.save(techStacks);
    }
}
