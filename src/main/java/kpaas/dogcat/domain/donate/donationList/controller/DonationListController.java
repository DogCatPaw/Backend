package kpaas.dogcat.domain.donate.donationList.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.donate.donationList.service.DonationListCommandService;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListReqDto;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListResDto;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "후원 관련 API")
@RestController
@RequestMapping("/api/donation")
@RequiredArgsConstructor
public class DonationListController {

    private final DonationListCommandService donationListCommandService;

    @Operation(summary = "후원하기", description = "선택한 뼈다귀 금액만큼 후원하는 API 입니다.")
    @PostMapping
    public CustomResponse<DonationListResDto.Donate> donate(@RequestBody DonationListReqDto.Donate dto) {
        return CustomResponse.onSuccess(SuccessCode.OK, donationListCommandService.donate(dto));
    }


}
