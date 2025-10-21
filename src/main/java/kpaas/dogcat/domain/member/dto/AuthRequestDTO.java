package kpaas.dogcat.domain.member.dto;

import kpaas.dogcat.domain.member.enums.Gender;
import kpaas.dogcat.domain.member.enums.Role;
import kpaas.dogcat.domain.member.enums.Type;
import lombok.Getter;

public class AuthRequestDTO {

    @Getter
    public static class SignupRequestDTO {
        private String walletAddress;
        private String username;
        private String nickname;
        private Gender gender;
        private int old;
        private String address;
        private String phoneNumber;
        private Type type;
        private String email;
        private String profileUrl;
        private Role role;
    }

    @Getter
    public static class LoginRequestDTO {
        private String walletAddress;
    }

    @Getter
    public static class ReissueRequestDTO {
        private Long id;
        private String refreshToken;
    }
}
