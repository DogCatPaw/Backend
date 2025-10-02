package kpaas.dogcat.domain.story.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResDTO {
    private Long memberId;
    private Long commentId;
    private String savedComment;
}
