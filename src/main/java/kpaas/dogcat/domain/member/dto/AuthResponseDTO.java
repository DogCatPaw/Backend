package kpaas.dogcat.domain.member.dto;

import kpaas.dogcat.domain.member.enums.Role;
import lombok.*;

public class AuthResponseDTO {

    @Getter
    @Builder
    public static class SignupResponseDTO {
        private String walletAddress;
        private String nickname;
    }

    @Getter
    @Builder
    public static class LoginResponseDTO {
        private String walletAddress;
        private String nickname;
        private Role role;
    }

    @Getter
    @Builder
    public static class ReissueResponseDTO {
        private Long id;
        private String nickname;
        private String accessToken;
    }
}
