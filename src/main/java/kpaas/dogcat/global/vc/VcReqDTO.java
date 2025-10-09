package kpaas.dogcat.global.vc;

import kpaas.dogcat.domain.pet.enums.Breed;
import kpaas.dogcat.domain.pet.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class VcReqDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PetVcSyncReqDTO {
        private String memberWallet;
        private List<String> vcJwt;
    }

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
        private String specifics;       // 특이 사항
        private String issuer;
    }
}
