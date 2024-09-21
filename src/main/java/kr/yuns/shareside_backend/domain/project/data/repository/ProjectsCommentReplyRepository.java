package kr.yuns.shareside_backend.domain.project.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.yuns.shareside_backend.domain.project.data.entity.ProjectsComment;
import kr.yuns.shareside_backend.domain.project.data.entity.ProjectsCommentReply;
import java.util.List;

public interface ProjectsCommentReplyRepository extends JpaRepository<ProjectsCommentReply, Long> {
    List<ProjectsCommentReply> findAllProjectsCommentReplyByProjectsComment(ProjectsComment projectsComment);
}
