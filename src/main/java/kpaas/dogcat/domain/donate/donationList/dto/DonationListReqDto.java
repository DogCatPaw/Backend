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
    public static class Donate {
        public Long memberId;
        public Long itemId;
        public Long donationId;
    }
}
