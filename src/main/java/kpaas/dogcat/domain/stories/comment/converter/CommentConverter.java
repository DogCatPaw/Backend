package kpaas.dogcat.domain.stories.comment.converter;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.stories.comment.dto.CommentReqDTO;
import kpaas.dogcat.domain.stories.comment.dto.CommentResDTO;
import kpaas.dogcat.domain.stories.comment.entity.Comment;
import kpaas.dogcat.domain.stories.story.entity.Story;
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

    public CommentResDTO.WriteDTO toCommentResDTO(String memberId, Comment comment) {
        return CommentResDTO.WriteDTO.builder()
                .memberId(memberId)
                .commentId(comment.getId())
                .savedComment(comment.getComment())
                .build();
    }

    public CommentResDTO.GetCommentDTO toGetCommentDTO(Comment comment) {
        return CommentResDTO.GetCommentDTO.builder()
                .nickName(comment.getMember().getNickname())
//                .profileUrl(comment.getMember().getProfileUrl())
                .storyId(comment.getStory().getId())
                .commentId(comment.getId())
                .savedComment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
