package kpaas.dogcat.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kpaas.dogcat.domain.member.dto.AuthRequestDTO;
import kpaas.dogcat.domain.member.dto.AuthResponseDTO;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원가입/로그인 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthCommandService authCommandService;

    @Operation(summary = "회원가입", description = "회원가입합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON201", description = "성공입니다"),
            @ApiResponse(responseCode = "WALLET409", description = "이미 가입된 회원입니다."),
            @ApiResponse(responseCode = "DUPLICATED_NICKNAME", description = "중복된 닉네임입니다."),
    })
    @PostMapping("/signup")
    public CustomResponse<AuthResponseDTO.SignupResponseDTO> signup(@RequestBody AuthRequestDTO.SignupRequestDTO dto) {
        AuthResponseDTO.SignupResponseDTO signupResponseDTO = authCommandService.signUp(dto);
        return CustomResponse.onSuccess(SuccessCode.CREATED, signupResponseDTO);
    }

    @Operation(summary = "로그인", description = "로그인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_NOTFOUND", description = "회원이 없습니다.")
    })
    @PostMapping("/login")
    public CustomResponse<AuthResponseDTO.LoginResponseDTO> login(@RequestBody AuthRequestDTO.LoginRequestDTO dto) {
        AuthResponseDTO.LoginResponseDTO loginResponseDTO = authCommandService.login(dto);
        return CustomResponse.onSuccess(SuccessCode.OK, loginResponseDTO);
    }

    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "TOKEN401", description = "유효한 토큰이 아닙니다."),
            @ApiResponse(responseCode = "BLACKLISTED", description = "블랙리스트 처리된 액세스토큰입니다.")
    })
    @PostMapping("/logout")
    public CustomResponse<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authCommandService.logout(request, response);
        return CustomResponse.onSuccess(SuccessCode.OK);
    }
}
