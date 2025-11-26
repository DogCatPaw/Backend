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
        public String memberId;
        public Long itemId;
        public Long donationId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DonationDto {
        private String memberId;
        private Long donationId;
    }
}
