package kpaas.dogcat.domain.member.converter;

import kpaas.dogcat.domain.member.dto.MemberResDto;
import kpaas.dogcat.domain.stories.story.entity.Story;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public MemberResDto.StoryDto toStoryDto(Story story,
                                            Long likeCount,
                                            Long commentCount) {
        return MemberResDto.StoryDto.builder()
                .storyId(story.getId())
                .title(story.getTitle())
                .likes(likeCount)
                .comments(commentCount)
                .build();
    }
}
