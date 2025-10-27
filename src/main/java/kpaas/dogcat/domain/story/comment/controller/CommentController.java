package kpaas.dogcat.domain.story.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.story.comment.dto.CommentReqDTO;
import kpaas.dogcat.domain.story.comment.dto.CommentResDTO;
import kpaas.dogcat.domain.story.comment.service.CommentCommandService;
import kpaas.dogcat.domain.story.comment.service.CommentQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.auth.CurrentWalletAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "일지 좋아요, 댓글 관련 API")
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @Operation(summary = "댓글 작성", description = "댓글 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON201", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_404", description = "회원이 없습니다."),
            @ApiResponse(responseCode = "STORY_404", description = "등록된 일상 일지가 없습니다.")
    })
    @PostMapping("/")
    public CustomResponse<CommentResDTO.WriteDTO> write(@Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
                                                        @RequestBody CommentReqDTO dto){
        return CustomResponse.onSuccess(SuccessCode.OK, commentCommandService.writeComment(walletAddress, dto));
    }

    @Operation(summary = "댓글 조회", description = "작성된 댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "STORY_404", description = "등록된 일상 일지가 없습니다.")
    })
    @GetMapping("/")
    public CustomResponse<CommentResDTO.GetCommentListDTO> getComment(@RequestParam Long storyId,
                                                                      @RequestParam(required = false) Long cursor,
                                                                      @RequestParam(defaultValue = "5") int size){
        return CustomResponse.onSuccess(SuccessCode.OK, commentQueryService.getComments(storyId, cursor, size));
    }

    @Operation(summary = "댓글 삭제", description = "작성된 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_404", description = "회원이 없습니다."),
            @ApiResponse(responseCode = "COMMENT_404", description = "해당되는 댓글이 없습니다."),
            @ApiResponse(responseCode = "COMMON401", description = "삭제할 권한이 없습니다.")

    })
    @DeleteMapping("/{commentId}")
    public CustomResponse<?> deleteComment(@PathVariable Long commentId,
                                           @Parameter(hidden = true) @CurrentWalletAddress String walletAddress){
        commentCommandService.delete(commentId, walletAddress);
        return CustomResponse.onSuccess(SuccessCode.OK);
    }
}