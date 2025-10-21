package kpaas.dogcat.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kpaas.dogcat.domain.member.dto.AuthRequestDTO;
import kpaas.dogcat.domain.member.dto.AuthResponseDTO;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입/로그인 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthCommandService authCommandService;

    @Operation(summary = "회원가입", description = "회원가입합니다.")
    @PostMapping("/signup")
    public CustomResponse<AuthResponseDTO.SignupResponseDTO> signup(@RequestBody AuthRequestDTO.SignupRequestDTO dto) {
        AuthResponseDTO.SignupResponseDTO signupResponseDTO = authCommandService.signUp(dto);
        return CustomResponse.onSuccess(SuccessCode.CREATED, signupResponseDTO);
    }

    @Operation(summary = "로그인", description = "로그인합니다.")
    @PostMapping("/login")
    public CustomResponse<AuthResponseDTO.LoginResponseDTO> login(@RequestBody AuthRequestDTO.LoginRequestDTO dto) {
        AuthResponseDTO.LoginResponseDTO loginResponseDTO = authCommandService.login(dto);
        return CustomResponse.onSuccess(SuccessCode.OK, loginResponseDTO);
    }

    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    @PostMapping("/logout")
    public CustomResponse<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authCommandService.logout(request, response);
        return CustomResponse.onSuccess(SuccessCode.OK);
    }
}
