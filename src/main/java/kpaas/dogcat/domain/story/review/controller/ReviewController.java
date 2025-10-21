package kpaas.dogcat.domain.story.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.story.review.dto.ReviewReqDTO;
import kpaas.dogcat.domain.story.review.dto.ReviewResDto;
import kpaas.dogcat.domain.story.review.service.ReviewCommandService;
import kpaas.dogcat.domain.story.review.service.ReviewQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.auth.CurrentWalletAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "입양 후기 일지 API")
@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;

    @Operation(summary = "입양 후기 일지 작성", description = "일지 하나를 작성합니다.")
    @PostMapping(value = "/review")
    public CustomResponse<ReviewResDto.WriteReviewResDto> create(
            @CurrentWalletAddress String walletAddress,
            @RequestBody ReviewReqDTO.WriteReviewDTO dto){
        ReviewResDto.WriteReviewResDto createdReview = reviewCommandService.writeReview(walletAddress, dto);
        return CustomResponse.onSuccess(SuccessCode.CREATED, createdReview);
    }

    @Operation(summary = "입양 후기 상세 조회", description = "입양 후기 한 개의 상세 내용을 조회합니다.")
    @GetMapping("/review/{reviewId}")
    public CustomResponse<ReviewResDto.ReviewDetailDto> getReview(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
            @PathVariable Long reviewId) {
        String memberId = (walletAddress != null) ? walletAddress : null;
        return CustomResponse.onSuccess(SuccessCode.OK, reviewQueryService.getReviewDetail(reviewId, memberId));
    }

    @Operation(summary = "메인화면 - 입양 후기 목록 조회", description = "입양 후기 일지 메인 화면의 목록을 조회합니다.")
    @GetMapping("/review/reviews")
    public CustomResponse<ReviewResDto.ReviewListDto> search(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "9") int size) {
        String memberId = (walletAddress != null) ? walletAddress : null;
        ReviewResDto.ReviewListDto searchResult = reviewQueryService.search(keyword, cursorId, size, memberId);
        return CustomResponse.onSuccess(SuccessCode.OK, searchResult);
    }
}
