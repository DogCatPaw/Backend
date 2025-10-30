package kpaas.dogcat.domain.stories.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.stories.review.dto.ReviewReqDTO;
import kpaas.dogcat.domain.stories.review.dto.ReviewResDto;
import kpaas.dogcat.domain.stories.review.service.ReviewCommandService;
import kpaas.dogcat.domain.stories.review.service.ReviewQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.auth.CurrentWalletAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "입양 후기 API")
@RestController
@RequestMapping("/api/story/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;

    @Operation(summary = "입양 후기 작성", description = "일지 하나를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON201", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_404", description = "회원이 없습니다."),
            @ApiResponse(responseCode = "PET_404", description = "등록된 반려동물이 없습니다. 등록 먼저 해주세요!")
    })
    @PostMapping()
    public CustomResponse<ReviewResDto.WriteReviewResDto> create(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
            @RequestBody ReviewReqDTO.WriteReviewDTO dto){
        ReviewResDto.WriteReviewResDto createdReview = reviewCommandService.writeReview(walletAddress, dto);
        return CustomResponse.onSuccess(SuccessCode.CREATED, createdReview);
    }

    @Operation(summary = "입양 후기 상세 조회", description = "입양 후기 한 개의 상세 내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "STORY_404", description = "등록된 입양 후기가 없습니다.")
    })
    @GetMapping("/{reviewId}")
    public CustomResponse<ReviewResDto.ReviewDetailDto> getReview(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
            @PathVariable Long reviewId) {
        String memberId = (walletAddress != null) ? walletAddress : null;
        return CustomResponse.onSuccess(SuccessCode.OK, reviewQueryService.getReviewDetail(reviewId, memberId));
    }

    @Operation(summary = "메인화면 - 입양 후기 목록 조회", description = "입양 후기 일지 메인 화면의 목록을 조회합니다.")
    @GetMapping("/reviews")
    public CustomResponse<ReviewResDto.ReviewListDto> search(
            @RequestParam(required = false) String walletAddress,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "9") int size){
        String memberId = (walletAddress != null) ? walletAddress : null;
        ReviewResDto.ReviewListDto searchResult = reviewQueryService.search(keyword, cursorId, size, memberId);
        return CustomResponse.onSuccess(SuccessCode.OK, searchResult);
    }

    @Operation(summary = "입양 후기 삭제", description = "작성된 입양 후기를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_404", description = "회원이 없습니다."),
            @ApiResponse(responseCode = "STORY_404", description = "등록된 입양 후기가 없습니다."),
            @ApiResponse(responseCode = "COMMON401", description = "삭제할 권한이 없습니다.")

    })
    @DeleteMapping("/{storyId}")
    public CustomResponse<?> deleteStory(@PathVariable Long storyId,
                                         @Parameter(hidden = true) @CurrentWalletAddress String walletAddress){
        reviewCommandService.delete(storyId, walletAddress);
        return CustomResponse.onSuccess(SuccessCode.OK);
    }
}
