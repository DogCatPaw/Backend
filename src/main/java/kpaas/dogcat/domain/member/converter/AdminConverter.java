package kpaas.dogcat.domain.member.converter;

import kpaas.dogcat.domain.member.dto.AdminResDto;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.dto.PetResDto;
import kpaas.dogcat.domain.pet.entity.Pet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminConverter {

    public AdminResDto.MemberResponseDto toAdminResDto(Member member) {
        List<PetResDto.RegisterPetResDto> petDtos = member.getPets().stream()
                .map(this::toRegisterPetResDto)
                .toList();

        return AdminResDto.MemberResponseDto.builder()
                .nickname(member.getNickname())
                .walletAddress(member.getId())
                .pets(petDtos)
                .build();
    }

    public PetResDto.RegisterPetResDto toRegisterPetResDto(Pet pet) {
        return PetResDto.RegisterPetResDto.builder()
                .petId(pet.getId())
                .petName(pet.getPetName())
                .did(pet.getDid())
                .build();
    }

    public AdminResDto.MemberListResponseDto toMemberListResponseDto(List<Member> members) {
        List<AdminResDto.MemberResponseDto> memberDtos = members.stream()
                .map(this::toAdminResDto)
                .toList();

        return AdminResDto.MemberListResponseDto.builder()
                .members(memberDtos)
                .nextCursor(null)
                .build();
    }
}
