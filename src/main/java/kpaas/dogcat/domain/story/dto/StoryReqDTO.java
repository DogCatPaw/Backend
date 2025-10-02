package kpaas.dogcat.domain.story.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class StoryReqDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(name = "story")
    public static class writeStoryReqDTO {
        private String petDid;
        private String title;
        private String content;
    }
}