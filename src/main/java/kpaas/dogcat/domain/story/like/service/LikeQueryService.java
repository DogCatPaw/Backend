package kpaas.dogcat.domain.story.like.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.story.Story;
import kpaas.dogcat.domain.story.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeQueryService {

    private final LikeRepository likeRepository;

    public Long getLikeCount(Long storyId){
        return likeRepository.countByStoryId(storyId);
    }

    public boolean isAlreadyLike(Story story, Member member) {
        return likeRepository.existsByStoryIdAndMemberId(story.getId(), member.getId());
    }
}
