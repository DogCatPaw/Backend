package kpaas.dogcat.domain.donate.donation.dto;

import kpaas.dogcat.domain.donate.donation.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class DonationReqDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CreateDto {
        private String memberId;
        private Long petId;
        private String title;
        private Integer targetAmount;
        private LocalDate deadline;
        private Category category;
        private String content;
        private String bankName;        //은행
        private String accountNumber;   //계좌
        private String accountHolder;   //예금주명
    }
}
