package kpaas.dogcat.global.vc;

import kpaas.dogcat.domain.pet.enums.Breed;
import kpaas.dogcat.domain.pet.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class VcResDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PetVcDTO {
        private String DID;
        private String petName;
        private Breed breed;
        private int old;
        private int weight;
        private Gender gender;
        private String color;
        private boolean isNeutral;      // 중성화 여부
        private String specifics;       // 특이사항
        private String issuer;
    }
}
