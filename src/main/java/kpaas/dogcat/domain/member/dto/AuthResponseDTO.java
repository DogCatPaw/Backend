package kpaas.dogcat.domain.member.dto;

import lombok.*;

import java.util.List;

public class AuthResponseDTO {

    @Getter
    @Builder
    public static class SignupResponseDTO {
        private String id;
        private String walletAddress;
        private String nickname;
    }

    @Getter
    @Builder
    public static class LoginResponseDTO {
        private String id;
        private String nickname;
//        private String accessToken;
//        private String refreshToken;
    }

    @Getter
    @Builder
    public static class ReissueResponseDTO {
        private Long id;
        private String nickname;
        private String accessToken;
    }
}
