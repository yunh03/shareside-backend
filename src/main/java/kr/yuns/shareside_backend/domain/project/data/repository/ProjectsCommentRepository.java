package kr.yuns.shareside_backend.domain.project.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.yuns.shareside_backend.domain.project.data.entity.ProjectsComment;
import kr.yuns.shareside_backend.domain.project.data.entity.SideProjects;
import java.util.List;

public interface ProjectsCommentRepository extends JpaRepository<ProjectsComment, Long> {
    List<ProjectsComment> findAllProjectsCommentBySideProjects(SideProjects sideProjects);
}
