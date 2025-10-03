package kpaas.dogcat.domain.story.comment.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.story.comment.converter.CommentConverter;
import kpaas.dogcat.domain.story.comment.dto.CommentReqDTO;
import kpaas.dogcat.domain.story.comment.dto.CommentResDTO;
import kpaas.dogcat.domain.story.comment.entity.Comment;
import kpaas.dogcat.domain.story.Story;
import kpaas.dogcat.domain.story.comment.repository.CommentRepository;
import kpaas.dogcat.domain.story.StoryRepository;
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

    public CommentResDTO.WriteDTO writeComment(Long memberId, CommentReqDTO dto){
        Story story = storyRepository.findById(dto.getStoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.DAILYSTORY_NOTFOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        changeCommentCount(story.getId());
        Comment comment = commentConverter.toComment(member, story, dto);
        commentRepository.save(comment);

        return commentConverter.toCommentResDTO(memberId, comment);
    }

    public Long changeCommentCount(Long storyId){
        return commentQueryService.getCommentCount(storyId) + 1;
    }
}
