package kpaas.dogcat.domain.donate.donationList.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class DonationListResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DonateDto {
        public Long memberId;
        public Long donationId;
        public Integer donationAmount;  // 후원 금액
        public Integer boneBalance;     // 남은 금액
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DonationDto {
        private String nickname;
        private String profileUrl;      // 아직 미구현
        private Integer donationAmount;
        private LocalDateTime donationTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DonationListDto {
        List<DonationDto> donations;
        private Long cursor;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MyBoneBalanceDto {
        private Integer currentBoneBalance;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MyDonationDto {
        private String donationTitle;
        private Integer donationAmount;
        private LocalDateTime donationTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MyDonationListDto {
        private Integer totalAmount;
        private Integer currentBoneBalance;
        List<MyDonationDto> donations;
        private Long cursor;
    }
}
