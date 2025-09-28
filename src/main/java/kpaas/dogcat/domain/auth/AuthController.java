package kpaas.dogcat.domain.auth;


import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.member.dto.AuthResponseDTO;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "회원가입 관련 API")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public CustomResponse<?> login(@RequestBody String walletAddress){
        if(authService.login(walletAddress)){
            return CustomResponse.onSuccess(SuccessCode.OK);
        } else {
            return CustomResponse.onFailure(ErrorCode.MEMBER_NOTFOUND);
        }
    }

    @PostMapping("/signup")
    public CustomResponse<?> register(@RequestBody String walletAddress){
        if(authService.register(walletAddress)){
            return CustomResponse.onSuccess(SuccessCode.CREATED);
        } else {
            return CustomResponse.onFailure(ErrorCode.DUPLICATED_MEMBER);
        }
    }
}
