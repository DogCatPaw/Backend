package kpaas.dogcat.domain.stories.comment.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.domain.stories.comment.converter.CommentConverter;
import kpaas.dogcat.domain.stories.comment.dto.CommentReqDTO;
import kpaas.dogcat.domain.stories.comment.dto.CommentResDTO;
import kpaas.dogcat.domain.stories.comment.entity.Comment;
import kpaas.dogcat.domain.stories.story.entity.Story;
import kpaas.dogcat.domain.stories.comment.repository.CommentRepository;
import kpaas.dogcat.domain.stories.story.repository.StoryRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentCommandService {

    private final StoryRepository storyRepository;
    private final CommentQueryService commentQueryService;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final CommentConverter commentConverter;
    private final AuthCommandService authCommandService;

    public CommentResDTO.WriteDTO writeComment(String memberId, CommentReqDTO dto){
        Story story = storyRepository.findById(dto.getStoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.DAILYSTORY_NOTFOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        changeCommentCount(story.getId());
        Comment comment = commentConverter.toComment(member, story, dto);
        commentRepository.save(comment);
        log.info("[ 댓글 작성 - 사용자: {}, 내용: {} ]", memberId, comment.getComment());

        return commentConverter.toCommentResDTO(memberId, comment);
    }

    public Long changeCommentCount(Long storyId){
        return commentQueryService.getCommentCount(storyId) + 1;
    }

    public void delete(Long commentId, String walletAddress) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOTFOUND));
        Member member = authCommandService.findById(walletAddress);

        // 권한 확인: 본인 댓글이거나 스토리 작성자만 삭제 가능
        boolean isCommentWriter = comment.getMember().equals(member);
        boolean isStoryWriter = comment.getStory().getMember().equals(member);

        if (!isCommentWriter && !isStoryWriter) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_401);
        }

        commentRepository.delete(comment);
        log.info("[ 댓글 삭제 완료 - 스토리: {}, 댓글: {}", comment.getStory(), commentId);
    }
}
