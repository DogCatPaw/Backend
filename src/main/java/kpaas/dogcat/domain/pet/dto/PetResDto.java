package kpaas.dogcat.domain.pet.dto;

import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
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
        private String memberId;
        private Long petId;
        private String did;
        private String petName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MyPetDto {
        private Long petId;
        private String did;
        private String petProfile;
        private String petName;
        private int old;
        private Gender gender;
        private Breed breed;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PetChatDto {
        private Long petId;
        private Long adoptId;
        private String did;
        private String petProfile;
        private String petName;
        private int old;
        private AdoptionStatus status;
        private Gender gender;
        private Breed breed;
    }
}
