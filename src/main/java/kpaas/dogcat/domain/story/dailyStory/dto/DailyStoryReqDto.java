package kpaas.dogcat.domain.story.dailyStory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DailyStoryReqDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(name = "story")
    public static class WriteStoryReqDto {
        private Long petId;
        private String title;
        private String content;
    }
}