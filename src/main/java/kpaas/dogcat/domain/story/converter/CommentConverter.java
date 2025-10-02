package kpaas.dogcat.domain.story.converter;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.story.dto.CommentReqDTO;
import kpaas.dogcat.domain.story.dto.CommentResDTO;
import kpaas.dogcat.domain.story.entity.Comment;
import kpaas.dogcat.domain.story.entity.Story;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public Comment toComment(Member member, Story story, CommentReqDTO dto) {
        return Comment.builder()
                .member(member)
                .story(story)
                .comment(dto.getComment())
                .build();
    }

    public CommentResDTO toCommentResDTO(Long memberId, Comment comment) {
        return CommentResDTO.builder()
                .memberId(memberId)
                .commentId(comment.getId())
                .savedComment(comment.getComment())
                .build();
    }
}
