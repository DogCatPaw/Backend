package kpaas.dogcat.domain.pet.dto;

import kpaas.dogcat.domain.pet.enums.Breed;
import kpaas.dogcat.domain.pet.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PetReqDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class registerPetReqDTO {
        private String did;
        private String petName;
        private Breed breed;
        private String customBreed;  // breed == OTHER 일 경우 사용자가 직접 입력한 품종
        private int old;
        private int weight;
        private Gender gender;
        private String color;
        private String feature;
        private String health;      // 중성화 여부
        private String specifics;
    }
}
