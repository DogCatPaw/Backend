package kpaas.dogcat.domain.story.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
