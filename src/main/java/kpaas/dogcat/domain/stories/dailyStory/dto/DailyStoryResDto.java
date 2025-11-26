package kpaas.dogcat.domain.stories.dailyStory.dto;

import kpaas.dogcat.domain.pet.enums.Breed;
import kpaas.dogcat.global.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class DailyStoryResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class WriteStoryResDto {
        private String memberName;
        private Long storyId;
        private Long petId;
        private String DID;
        private String title;
        private String images;
        private String content;
        private PostType postType;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoryDetailDto {
        private String profileUrl;
        private String memberName;
        private Long petId;
        private String DID;
        private String title;
        private String images;
        private String content;
        private Breed breed;
        private String petName;
        private Long likeCount;
        private boolean liked;
        private Long commentCount;
        private LocalDateTime createdAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoryPreviewDto {
        private Long storyId;
        private String profileUrl;
        private String memberName;
        private String title;
        private String images;
        private String content;
        private Breed breed;
        private String petName;
        private Long likeCount;
        private boolean liked;
        private Long commentCount;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoriesListDto {
        private List<StoryPreviewDto> stories;
        private Long nextCursor;
    }
}
