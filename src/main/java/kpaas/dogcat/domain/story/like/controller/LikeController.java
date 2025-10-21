package kpaas.dogcat.domain.story.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.story.like.dto.LikeResDTO;
import kpaas.dogcat.domain.story.like.service.LikeCommandService;
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
    @PostMapping("/")
    public CustomResponse<LikeResDTO> createLike(@Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
                                                 @RequestParam Long storyId){
        return CustomResponse.onSuccess(SuccessCode.OK, likeCommandService.createLike(storyId, walletAddress));
    }
}
