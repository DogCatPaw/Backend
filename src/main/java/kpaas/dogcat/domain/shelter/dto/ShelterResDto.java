package kpaas.dogcat.domain.shelter.dto;

import kpaas.dogcat.domain.adopt.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ShelterResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ShelterPreviewDto {
        private Long shelterId;
        private String shelterName;
        private Region region;
        private String district;
        private String address;
        private String contact;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ShelterPreviewListDto {
        private List<ShelterPreviewDto> shelters;
        private Long nextCursor;
    }
}
