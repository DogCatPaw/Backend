package kpaas.dogcat.domain.donate.donation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.donate.donation.dto.DonationReqDto;
import kpaas.dogcat.domain.donate.donation.dto.DonationResDto;
import kpaas.dogcat.domain.donate.donation.enums.DonationStatus;
import kpaas.dogcat.domain.donate.donation.service.DonationCommandService;
import kpaas.dogcat.domain.donate.donation.service.DonationQueryService;
import kpaas.dogcat.domain.pet.enums.Breed;
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
    @PostMapping(value = "/posts")
    public CustomResponse<DonationResDto.CreateDto> create(@RequestBody DonationReqDto.CreateDto dto) {
        return CustomResponse.onSuccess(SuccessCode.CREATED, donationCommandService.createDonation(dto));
    }

    @Operation(summary = "후원 공고 글 상세 보기 + 후원 내역 조회", description = "후원 공고글을 상세 보기하는 API 입니다." +
            "후원 내역은 기본적으로 5개를 반환하며, 조정이 가능합니다.")
    @GetMapping("/{donationId}")
    public CustomResponse<DonationResDto.DetailDto> getDonation(@PathVariable Long donationId,
                                                                @RequestParam(required = false) Long cursor,
                                                                @RequestParam(defaultValue = "5") int size) {
        DonationResDto.DetailDto donationDetail = donationQueryService.getDonationDetail(donationId, cursor, size);
        return CustomResponse.onSuccess(SuccessCode.OK, donationDetail);
    }

//    @Operation(summary = "상단 - 마감 임박 후원 3개", description = "마감 기한이 임박한 후원 3개를 조회하는 API 입니다. "
//                                                    + "후원페이지 상단에 띄워주세요. ")
//    @GetMapping("/closing")
//    public CustomResponse<List<DonationResDto.PreviewDto>> getClosingSoonDonations() {
//        return CustomResponse.onSuccess(SuccessCode.OK, donationQueryService.get3ClosingSoonDonations());
//    }

    @Operation(summary = "메인 화면 - 후원 공고 조회 (cursor 기반)", description = "후원글을 cursor로 조회하는 API 입니다." +
            "status 파라미터는 ACTIVE, ACHIEVED, CLOSED처럼 대문자로 보내주세요." +
            "cursor와 size에 아무 값도 입력하지 않아도 되며, 기본 사이즈는 9입니다. 다음 조회는 nextCursor을 사용하세요.")
    @GetMapping("/list")
    public CustomResponse<DonationResDto.DonationPreviewListDto> getDonationList(@RequestParam(required = false) Long cursor,
                                                                                 @RequestParam(defaultValue = "9") int size,
                                                                                 @RequestParam(required = false) Breed breed,
                                                                                 @RequestParam(required = false) DonationStatus status) {
        return CustomResponse.onSuccess(donationQueryService.getDonations(cursor, size, breed, status));
    }
}
