package kr.yuns.shareside_backend.domain.project.data.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadCommentsRequestDto {
    private String comment;
    private Long projectId;
    private boolean isServiceUser;
}
