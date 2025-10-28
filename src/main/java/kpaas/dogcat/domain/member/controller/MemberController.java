package kpaas.dogcat.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.member.dto.MemberReqDto;
import kpaas.dogcat.domain.member.dto.MemberResDto;
import kpaas.dogcat.domain.member.service.MemberCommandService;
import kpaas.dogcat.domain.member.service.MemberQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.auth.CurrentWalletAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @Operation(summary = "회원가입", description = "회원가입합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON201", description = "성공입니다"),
            @ApiResponse(responseCode = "WALLET409", description = "이미 가입된 회원입니다."),
            @ApiResponse(responseCode = "DUPLICATED_NICKNAME", description = "중복된 닉네임입니다."),
    })
    @PostMapping("/auth/signup")
    public CustomResponse<MemberResDto.SignupResDto> signup(@RequestBody MemberReqDto.SignupReqDto dto) {
        MemberResDto.SignupResDto signupResponseDTO = memberCommandService.signUp(dto);
        return CustomResponse.onSuccess(SuccessCode.CREATED, signupResponseDTO);
    }

    @Operation(summary = "작성한 일지 조회(마이페이지)", description = "일상일지 / 입양 후기 일지 모두 조회하는 API입니다.")
    @GetMapping("/member")
    public CustomResponse<MemberResDto.StoriesListDto> getStories(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "9") int size){
        return CustomResponse.onSuccess(SuccessCode.OK, memberQueryService.getStories(walletAddress, cursor, size));
    }
}
