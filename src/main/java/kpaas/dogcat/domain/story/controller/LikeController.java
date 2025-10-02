package kpaas.dogcat.domain.story.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.story.dto.LikeResDTO;
import kpaas.dogcat.domain.story.service.LikeCommandService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "일상 일지 API")
@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeCommandService likeCommandService;

    @Operation(summary = "좋아요 누름", description = "좋아요 누릅니다. 이미 있으면 취소, 없으면 좋아요 생성")
    @PostMapping("/")
    public CustomResponse<LikeResDTO> createLike(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @RequestParam Long storyId){
        return CustomResponse.onSuccess(SuccessCode.OK, likeCommandService.createLike(storyId, userDetails.getId()));
    }
}
