package kpaas.dogcat.domain.story.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class WriteReviewResDTO {
        private String memberName;
        private Long storyId;
        private Long petId;
        private String DID;
        private String title;
        private String images;
        private String content;
        private String adoptionAgency;
        private LocalDate adoptionDate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReviewDTO {
        private String memberName;
        private Long storyId;
        private Long petId;
        private String DID;
        private String title;
        private String images;
        private String content;
        private Long likeCount;
        private boolean liked;
        private Long commentCount;
        private String adoptionAgency;
        private LocalDate adoptionDate;
        private LocalDateTime createdAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReviewListDTO {
        private List<ReviewResDTO.ReviewDTO> reviews;
        private Long nextCursor;
    }
}
