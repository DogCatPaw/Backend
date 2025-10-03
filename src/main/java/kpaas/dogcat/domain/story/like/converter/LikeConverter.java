package kpaas.dogcat.domain.story.like.converter;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.story.like.dto.LikeResDTO;
import kpaas.dogcat.domain.story.like.entity.Like;
import kpaas.dogcat.domain.story.Story;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LikeConverter {

    public Like toLike(Story story, Member member){
        return Like.builder()
                .story(story)
                .member(member)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public LikeResDTO toLikeResDTO(Long storyId, Long memberId, Long likeCount, boolean liked) {
        return LikeResDTO.builder()
                .storyId(storyId)
                .memberId(memberId)
                .likeCount(likeCount)
                .liked(liked)
                .build();
    }
}