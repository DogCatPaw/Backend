package kpaas.dogcat.domain.story.like.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.story.like.converter.LikeConverter;
import kpaas.dogcat.domain.story.like.dto.LikeResDTO;
import kpaas.dogcat.domain.story.like.entity.Like;
import kpaas.dogcat.domain.story.Story;
import kpaas.dogcat.domain.story.like.repository.LikeRepository;
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
public class LikeCommandService {

    private final StoryRepository storyRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final LikeQueryService likeQueryService;
    private final LikeConverter likeConverter;

    public LikeResDTO createLike(Long storyId, String memberId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new CustomException(ErrorCode.DAILYSTORY_NOTFOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        boolean alreadyLike = isAlreadyLike(story, member);
        Long likeCounts = likeQueryService.getLikeCount(storyId);

        return likeConverter.toLikeResDTO(storyId, memberId, likeCounts, !alreadyLike);
    }

    public boolean isAlreadyLike(Story story, Member member) {
        boolean alreadyLike = likeRepository.existsByStoryIdAndMemberId(story.getId(), member.getId());
        if (alreadyLike) {
            likeRepository.deleteByStoryIdAndMemberId(story.getId(), member.getId());
        } else {
            Like savedLike = likeConverter.toLike(story, member);
            likeRepository.save(savedLike);
        }
        return alreadyLike;
    }
}