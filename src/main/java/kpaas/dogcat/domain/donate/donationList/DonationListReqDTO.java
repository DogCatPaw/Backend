package kpaas.dogcat.domain.donate.donationList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DonationListReqDTO {

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
