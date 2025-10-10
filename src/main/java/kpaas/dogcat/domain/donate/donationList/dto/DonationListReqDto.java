package kpaas.dogcat.domain.donate.donationList.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DonationListReqDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DonateDto {
        public Long memberId;
        public Long itemId;
        public Long donationId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DonationDto {
        private Long memberId;
        private Long donationId;
    }
}
