package kpaas.dogcat.domain.donate.donationList.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.donate.donationList.service.DonationListCommandService;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListReqDto;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListResDto;
import kpaas.dogcat.domain.donate.donationList.service.DonationListQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
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
    @PostMapping
    public CustomResponse<DonationListResDto.DonateDto> donate(@RequestBody DonationListReqDto.DonateDto dto) {
        return CustomResponse.onSuccess(SuccessCode.OK, donationListCommandService.donate(dto));
    }

    @Operation(summary = "후원 공고 내 후원 목록 조회하기", description = "후원 공고 내 후원 목록을 조회하는 API 입니다.")
    @GetMapping("/lists")
    public CustomResponse<DonationListResDto.DonationListDto> getDonationList(@RequestParam(required = true) Long donationId,
                                                                              @RequestParam(required = false) Long cursor,
                                                                              @RequestParam(defaultValue = "5") int size) {
        return CustomResponse.onSuccess(SuccessCode.OK,
                donationListQueryService.getDonationList(donationId, cursor, size));
    }
}
