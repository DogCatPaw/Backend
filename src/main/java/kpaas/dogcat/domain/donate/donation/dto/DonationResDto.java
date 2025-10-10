package kpaas.dogcat.domain.donate.donation.dto;

import kpaas.dogcat.domain.donate.donation.enums.Category;
import kpaas.dogcat.domain.pet.enums.Breed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class DonationResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CreateDto {
        private Long memberId;
        private Long donationId;
        private String petName;
        private String petDid;
        private Breed breed;
        private String title;
        private Integer targetAmount;
        private LocalDate deadline;
        private Category category;
        private String content;
        private String images;
    }
}
