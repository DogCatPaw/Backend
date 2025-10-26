package kpaas.dogcat.domain.donate.donationList.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListReqDto;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListResDto;
import kpaas.dogcat.domain.donate.donationList.service.DonationListCommandService;
import kpaas.dogcat.domain.donate.donationList.service.DonationListQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.auth.CurrentWalletAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "후원 관련 API")
@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationListController {

    private final DonationListCommandService donationListCommandService;
    private final DonationListQueryService donationListQueryService;

    @Operation(summary = "후원하기", description = "선택한 뼈다귀 금액만큼 후원하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON201", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_404", description = "회원이 없습니다."),
            @ApiResponse(responseCode = "DONATION404", description = "해당되는 후원 공고가 없습니다."),
            @ApiResponse(responseCode = "ITEM404", description = "구매할 물품이 없습니다."),
            @ApiResponse(responseCode = "DONATION404", description = "해당 후원 공고는 마감되었습니다."),
            @ApiResponse(responseCode = "BONE404", description = "후원 가능한 뼈다귀가 없습니다.")
    })
    @PostMapping
    public CustomResponse<DonationListResDto.DonateDto> donate(@RequestBody DonationListReqDto.DonateDto dto) {
        return CustomResponse.onSuccess(SuccessCode.OK, donationListCommandService.donate(dto));
    }

    @Operation(summary = "내가 후원한 내역 조회하기(마이페이지)", description = "내 후원 내역을 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_404", description = "회원이 없습니다.")
    })
    @GetMapping("/mine")
    public CustomResponse<DonationListResDto.MyDonationListDto> getMyDonationList(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "5") int size) {
        return CustomResponse.onSuccess(SuccessCode.OK,
                donationListQueryService.getMyDonationList(walletAddress, cursor, size));
    }

    @Operation(summary = "현재 후원 가능한 뼈다귀 조회", description = "내 뼈다귀 잔여량을 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_404", description = "회원이 없습니다.")
    })
    @GetMapping("/bone")
    public CustomResponse<DonationListResDto.MyBoneBalanceDto> getMyBoneBalance(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress) {
        return CustomResponse.onSuccess(SuccessCode.OK, donationListQueryService.getMyBoneBalance(walletAddress));
    }
}
