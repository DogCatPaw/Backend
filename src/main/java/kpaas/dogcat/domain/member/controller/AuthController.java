package kpaas.dogcat.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kpaas.dogcat.domain.member.converter.AuthConverter;
import kpaas.dogcat.domain.member.dto.AuthRequestDTO;
import kpaas.dogcat.domain.member.dto.AuthResponseDTO;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "멍냥일지 Swagger API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthCommandService authCommandService;
    private final AuthConverter authConverter;

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

    @Operation(summary = "리이슈", description = "액세스 토큰을 재발행합니다. id와 refresh 필요")
    @PostMapping("/reissue")
    public CustomResponse<AuthResponseDTO.ReissueResponseDTO> reissue(@RequestBody AuthRequestDTO.ReissueRequestDTO dto) {
        AuthResponseDTO.ReissueResponseDTO reissue = authCommandService.reissue(dto);
        return CustomResponse.onSuccess(SuccessCode.OK, reissue);
    }

    @Operation(summary = "깃허브 액션 테스트")
    @GetMapping("/test")
    public String test(){
        return "github actions!!!!!!!!!";
    }
}
