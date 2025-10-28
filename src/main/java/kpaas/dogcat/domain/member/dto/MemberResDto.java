package kpaas.dogcat.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MemberResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoryDto {
        private Long storyId;
        private String title;
        private Long comments;
        private Long likes;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoriesListDto {
        private List<StoryDto> stories;
        private Long nextCursor;
    }
}
