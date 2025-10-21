package kpaas.dogcat.domain.member.converter;

import kpaas.dogcat.domain.member.dto.AuthRequestDTO;
import kpaas.dogcat.domain.member.dto.AuthResponseDTO;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class AuthConverter {

    public Member toSignupEntity(AuthRequestDTO.SignupRequestDTO dto) {
        return Member.builder()
                .id(dto.getWalletAddress())
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .gender(dto.getGender())
                .old(dto.getOld())
                .phoneNumber(String.valueOf(dto.getPhoneNumber()))
                .profileUrl(dto.getProfileUrl())
                .role(dto.getRole())
                .build();
    }

    public AuthResponseDTO.SignupResponseDTO toSignupResponseDTO(Member member) {
        return AuthResponseDTO.SignupResponseDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
