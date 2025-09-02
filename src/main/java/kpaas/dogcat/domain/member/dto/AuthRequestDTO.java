package kpaas.dogcat.domain.member.dto;

import kpaas.dogcat.domain.member.enums.Gender;
import kpaas.dogcat.domain.member.enums.Type;
import lombok.Getter;

public class AuthRequestDTO {

    @Getter
    public static class SignupRequestDTO {
        private String username;
        private String nickname;
        private Gender gender;
        private int old;
        private String address;
        private String phoneNumber;
        private Type type;
        private String loginId;
        private String password;
        private String email;
    }

    @Getter
    public static class LoginRequestDTO {
        private String loginId;
        private String password;
    }

    @Getter
    public static class ReissueRequestDTO {
        private Long id;
        private String refreshToken;
    }
}
