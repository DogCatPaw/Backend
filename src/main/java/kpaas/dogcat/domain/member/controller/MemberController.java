package kpaas.dogcat.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.member.dto.MemberResDto;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.domain.member.service.MemberQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.auth.CurrentWalletAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 마이페이지 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberQueryService memberQueryService;

    @Operation(summary = "작성한 일지 조회(마이페이지)", description = "일상일지 / 입양 후기 일지 모두 조회하는 API입니다.")
    @GetMapping("/member")
    public CustomResponse<MemberResDto.StoriesListDto> getStories(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "9") int size){
        return CustomResponse.onSuccess(SuccessCode.OK, memberQueryService.getStories(walletAddress, cursor, size));
    }
}
