package kpaas.dogcat.domain.member.dto;

import kpaas.dogcat.domain.pet.dto.PetResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class AdminResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberResponseDto {
        private String nickname;
        private String walletAddress;
        private List<PetResDto.RegisterPetResDto> pets;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberListResponseDto {
        private List<MemberResponseDto> members;
        private LocalDateTime nextCursor;
    }
}
