package kr.yuns.shareside_backend.domain.project.data.dto.request;

import kr.yuns.shareside_backend.domain.project.data.enums.ProjectCategory;
import kr.yuns.shareside_backend.domain.project.data.enums.TechStack;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class SideProjectUploadRequestDto {
    private String primaryImage;
    private List<TechStack> techStacks;
    private String title;
    private String description;
    private String contents;
    private String githubUrl;
    private String notionUrl;
    private String deployUrl;
    private ProjectCategory projectCategory;
}
