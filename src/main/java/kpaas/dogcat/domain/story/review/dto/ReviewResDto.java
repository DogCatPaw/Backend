package kpaas.dogcat.domain.story.review.dto;

import kpaas.dogcat.domain.pet.enums.Breed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class WriteReviewResDto {
        private String memberName;
        private Long storyId;
        private Long petId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReviewDetailDto {
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
        private String adoptionAgency;
        private LocalDate adoptionDate;
        private LocalDateTime createdAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReviewDto {
        private String profileUrl;
        private String memberName;
        private String images;
        private String title;
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
    public static class ReviewListDto {
        private List<ReviewDto> reviews;
        private Long nextCursor;
    }
}
