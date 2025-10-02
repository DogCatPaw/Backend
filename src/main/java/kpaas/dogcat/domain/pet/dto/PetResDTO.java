package kpaas.dogcat.domain.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PetResDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class registerPetResDTO {
        private Long memberId;
        private String did;
        private String petName;
    }
}
