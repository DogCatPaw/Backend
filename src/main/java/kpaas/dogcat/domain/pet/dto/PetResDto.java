package kpaas.dogcat.domain.pet.dto;

import kpaas.dogcat.domain.pet.enums.Breed;
import kpaas.dogcat.domain.pet.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PetResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class registerPetResDto {
        private Long memberId;
        private Long petId;
        private String did;
        private String petName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MyPetListDto {
        private Long petId;
        private String did;
        private String petProfile;
        private String petName;
        private int old;
        private int weight;
        private Gender gender;
        private Breed breed;
        private String color;
        private boolean isNeutral;
        private String specifics;
    }
}
