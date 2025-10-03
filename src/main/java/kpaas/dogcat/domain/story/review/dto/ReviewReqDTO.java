package kpaas.dogcat.domain.story.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ReviewReqDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(name = "review")
    public static class WriteReviewDTO {
        private String petDid;
        private String title;
        private String content;
        private String adoptionAgency;
        private LocalDate adoptionDate;
    }
}
