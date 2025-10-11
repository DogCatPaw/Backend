package kpaas.dogcat.domain.donate.donation.dto;

import kpaas.dogcat.domain.donate.donation.enums.Category;
import kpaas.dogcat.domain.donate.donation.enums.DonationStatus;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListResDto;
import kpaas.dogcat.domain.pet.enums.Breed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class DonationResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CreateDto {
        private Long memberId;
        private Long donationId;
        private String petDid;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DetailDto {
        private Long memberId;
        private String petName;
        private String petDid;
        private Breed breed;
        private String title;
        private Integer targetAmount;
        private Integer currentAmount;
        private DonationStatus donationStatus;
        private LocalDate deadline;
        private Category category;
        private String content;
        private String images;

        private List<DonationListResDto.DonationDto> recentDonations;
        private Long cursor;
    }
}
