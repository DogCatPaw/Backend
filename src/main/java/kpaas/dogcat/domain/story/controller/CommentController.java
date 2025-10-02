package kpaas.dogcat.domain.story.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.story.dto.CommentReqDTO;
import kpaas.dogcat.domain.story.dto.CommentResDTO;
import kpaas.dogcat.domain.story.service.CommentCommandService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "일상 일지 API")
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentCommandService commentCommandService;

    @Operation(summary = "댓글 작성", description = "댓글 작성합니다.")
    @PostMapping("/")
    public CustomResponse<CommentResDTO> write(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @RequestBody CommentReqDTO dto){
        return CustomResponse.onSuccess(SuccessCode.OK, commentCommandService.writeComment(userDetails.getId(), dto));
    }
}