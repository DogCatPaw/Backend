package kpaas.dogcat.domain.story.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.story.review.service.ReviewCommandService;
import kpaas.dogcat.domain.story.review.service.ReviewQueryService;
import kpaas.dogcat.domain.story.review.dto.ReviewReqDTO;
import kpaas.dogcat.domain.story.review.dto.ReviewResDTO;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "입양 후기 일지 API")
@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;

    @Operation(summary = "입양 후기 일지 작성", description = "일지 하나를 작성합니다.")
    @PostMapping(value = "/review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CustomResponse<ReviewResDTO.WriteReviewResDTO> create(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                 @RequestPart("story") ReviewReqDTO.WriteReviewDTO dto,
                                                                 @RequestPart(value = "image", required = false) MultipartFile image){
        ReviewResDTO.WriteReviewResDTO createdReview = reviewCommandService.writeReview(userDetails.getId(), dto, image);
        return CustomResponse.onSuccess(SuccessCode.CREATED, createdReview);
    }

    @Operation(summary = "입양 후기 하나 조회", description = "입양 후기 하나를 조회합니다.")
    @GetMapping("/review/{reviews}")
    public CustomResponse<ReviewResDTO.ReviewDTO> getReview(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                           @PathVariable Long reviews) {
        return CustomResponse.onSuccess(SuccessCode.OK, reviewQueryService.getReview(reviews, userDetails.getId()));
    }

    @Operation(summary = "메인 입양 후기 목록 조회", description = "입양 후기 일지 메인 화면의 목록을 조회합니다.")
    @GetMapping("/review/reviews")
    public CustomResponse<ReviewResDTO.ReviewListDTO> getReviews(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                 @RequestParam(required = false) Long cursorId,
                                                                 @RequestParam(defaultValue = "8") int size){
        return CustomResponse.onSuccess(SuccessCode.OK, reviewQueryService.getReviews(cursorId, size, userDetails.getId()));
    }
}
