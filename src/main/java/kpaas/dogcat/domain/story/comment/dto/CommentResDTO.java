package kpaas.dogcat.domain.story.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


public class CommentResDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class WriteDTO {
        private Long memberId;
        private Long commentId;
        private String savedComment;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetCommentDTO {
        private String nickName;
//        private String profileUrl;
        private Long storyId;
        private Long commentId;
        private String savedComment;
        private LocalDateTime createdAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetCommentListDTO {
        private List<GetCommentDTO> commentList;
        private Long nextCursor;
    }
}

