package kr.yuns.shareside_backend.domain.project.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.yuns.shareside_backend.domain.project.data.entity.ProjectsCommentReply;

public interface ProjectsCommentReplyRepository extends JpaRepository<ProjectsCommentReply, Long> {
    
}
