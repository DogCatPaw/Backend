package kpaas.dogcat.domain.adopt.dto;

import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class AdoptReqDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RegisterDto {
        private Long petId;
        private String title;
        private String content;
        private Region region;
        private String district;
        private String shelterName;
        private String contact;
        private LocalDate deadLine;
        private AdoptionStatus status;
    }
}
