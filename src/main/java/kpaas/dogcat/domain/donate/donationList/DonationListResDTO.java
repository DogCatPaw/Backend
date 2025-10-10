package kpaas.dogcat.domain.donate.donationList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DonationListResDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Donate {
        public Long memberId;
        public Long donationId;
        public Integer donationAmount;  // 후원 금액
        public Integer boneBalance;     // 남은 금액
    }
}
