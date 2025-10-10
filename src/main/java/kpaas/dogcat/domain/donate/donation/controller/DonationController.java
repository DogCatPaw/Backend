package kpaas.dogcat.domain.donate.donation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.donate.donation.dto.DonationReqDto;
import kpaas.dogcat.domain.donate.donation.dto.DonationResDto;
import kpaas.dogcat.domain.donate.donation.service.DonationCommandService;
import kpaas.dogcat.domain.donate.donation.service.DonationQueryService;
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
public class DonationController {

    private final DonationCommandService donationCommandService;
    private final DonationQueryService donationQueryService;

    @Operation(summary = "후원 공고 글 작성하기", description = "후원 공고글을 작성하는 API 입니다.")
    @PostMapping("/")
    public CustomResponse<DonationResDto.CreateDto> create(@RequestBody DonationReqDto.CreateDto dto) {
        return CustomResponse.onSuccess(SuccessCode.CREATED, donationCommandService.createDonation(dto));
    }
}
