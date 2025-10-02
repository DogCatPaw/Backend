package kpaas.dogcat.domain.story.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class StoryResDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class writeStoryResDTO {
        private String memberName;
        private Long storyId;
        private String petDid;   //동물선택
        private String title;
        private String images;
        private String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoryPreviewDTO {
        private String memberName;
        private Long storyId;
        private String petDid;
        private String title;
        private String images;
        private String content;
        private Long likeCount;
        private boolean liked;
        private Long commentCount;
        private LocalDateTime createdAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoriesListDTO {
        private List<StoryPreviewDTO> stories;
        private Long nextCursor;       // 다음 요청에 사용할 커서 (null이면 끝)
    }
}
