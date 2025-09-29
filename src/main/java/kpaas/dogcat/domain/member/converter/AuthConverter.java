package kpaas.dogcat.domain.member.converter;

import kpaas.dogcat.domain.member.dto.AuthRequestDTO;
import kpaas.dogcat.domain.member.dto.AuthResponseDTO;
import kpaas.dogcat.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class AuthConverter {

    public Member toSignupEntity(AuthRequestDTO.SignupRequestDTO dto) {
        return Member.builder()
                .walletAddress(dto.getWalletAddress())
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .gender(dto.getGender())
                .old(dto.getOld())
                .address(dto.getAddress())
                .phoneNumber(String.valueOf(dto.getPhoneNumber()))
                .type(dto.getType())
                .email(dto.getEmail())
                .build();
    }

    public AuthResponseDTO.SignupResponseDTO toSignupResponseDTO(Member member) {
        return AuthResponseDTO.SignupResponseDTO.builder()
                .id(member.getId())
                .walletAddress(member.getWalletAddress())
                .nickname(member.getNickname())
                .build();
    }
}
