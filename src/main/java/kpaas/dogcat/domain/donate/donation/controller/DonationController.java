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
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "후원 공고 글 상세 보기 + 후원 내역 조회", description = "후원 공고글을 상세 보기하는 API 입니다.")
    @GetMapping("/")
    public CustomResponse<DonationResDto.DetailDto> getDonation(@RequestParam Long donationId,
                                                                @RequestParam(required = false) Long cursor,
                                                                @RequestParam(defaultValue = "5") int size) {
        DonationResDto.DetailDto donationDetail = donationQueryService.getDonationDetail(donationId, cursor, size);
        return CustomResponse.onSuccess(SuccessCode.OK, donationDetail);
    }
}
