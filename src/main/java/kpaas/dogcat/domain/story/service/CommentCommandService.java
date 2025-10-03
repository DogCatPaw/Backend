package kpaas.dogcat.domain.story.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.story.converter.CommentConverter;
import kpaas.dogcat.domain.story.dto.CommentReqDTO;
import kpaas.dogcat.domain.story.dto.CommentResDTO;
import kpaas.dogcat.domain.story.entity.Comment;
import kpaas.dogcat.domain.story.entity.Story;
import kpaas.dogcat.domain.story.repository.CommentRepository;
import kpaas.dogcat.domain.story.repository.StoryRepository;
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
                .orElseThrow(() -> new CustomException(ErrorCode.STORY_NOTFOUND));
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
