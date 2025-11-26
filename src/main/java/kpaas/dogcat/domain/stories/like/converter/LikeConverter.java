package kpaas.dogcat.domain.stories.like.converter;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.stories.like.dto.LikeResDTO;
import kpaas.dogcat.domain.stories.like.entity.Like;
import kpaas.dogcat.domain.stories.story.entity.Story;
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

    public LikeResDTO toLikeResDTO(Long storyId, String memberId, Long likeCount, boolean liked) {
        return LikeResDTO.builder()
                .storyId(storyId)
                .memberId(memberId)
                .likeCount(likeCount)
                .liked(liked)
                .build();
    }
}