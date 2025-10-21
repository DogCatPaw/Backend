package kpaas.dogcat.domain.member.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.member.dto.AdminResDto;
import kpaas.dogcat.domain.member.dto.AuthResponseDTO;
import kpaas.dogcat.domain.member.service.AdminQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.auth.CurrentWalletAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "관리자 API")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminQueryService adminQueryService;

    /** 회원id와 반려동물id 조회*/
    public CustomResponse<AdminResDto.MemberListResponseDto> getMembers(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursor,
            @RequestParam(defaultValue = "10") int size) {
        adminQueryService.isAdmin(walletAddress);
        return CustomResponse.onSuccess(SuccessCode.OK, adminQueryService.memberList(cursor, size));
    }
}
