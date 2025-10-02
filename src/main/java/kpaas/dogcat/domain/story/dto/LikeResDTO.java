package kpaas.dogcat.domain.story.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeResDTO {
    private Long storyId;
    private Long memberId;
    private Long likeCount;
    private boolean liked;
}
