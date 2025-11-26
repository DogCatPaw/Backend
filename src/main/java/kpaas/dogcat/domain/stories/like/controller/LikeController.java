package kpaas.dogcat.domain.stories.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.stories.like.dto.LikeResDTO;
import kpaas.dogcat.domain.stories.like.service.LikeCommandService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.auth.CurrentWalletAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "일지 좋아요, 댓글 관련 API")
@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeCommandService likeCommandService;

    @Operation(summary = "좋아요 누름", description = "좋아요 누릅니다. 이미 있으면 취소, 없으면 좋아요 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "STORY_404", description = "등록된 일상 일지가 없습니다.")
    })
    @PostMapping("/")
    public CustomResponse<LikeResDTO> createLike(@Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
                                                 @RequestParam Long storyId){
        return CustomResponse.onSuccess(SuccessCode.OK, likeCommandService.createLike(storyId, walletAddress));
    }
}
