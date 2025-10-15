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
        private String images;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DetailDto {
        private String dDay;
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
        private int patronCount;
        private int progress;

        private List<DonationListResDto.DonationDto> recentDonations;
        private Long cursor;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PreviewDto {
        private Long donationId;
        private String dDay;
        private String thumbnail;
        private String title;
        private Integer currentAmount;
        private Integer targetAmount;
        private DonationStatus donationStatus;
        private int patronCount;           //후원자 수
        private int progress;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PreviewListDto {
        private List<DonationResDto.PreviewDto> donations;
        private Long nextCursor;
    }
}
