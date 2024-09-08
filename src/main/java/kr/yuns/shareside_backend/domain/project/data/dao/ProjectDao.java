package kr.yuns.shareside_backend.domain.project.data.dao;

import kr.yuns.shareside_backend.domain.project.data.entity.SideProjects;
import kr.yuns.shareside_backend.domain.project.data.entity.TechStacks;

public interface ProjectDao {
    SideProjects saveSideProjectEntity(SideProjects sideProjects);
    SideProjects getSideProjectsEntityViaId(Long projectId);
    void saveTechStacksEntity(TechStacks techStacks);
}
