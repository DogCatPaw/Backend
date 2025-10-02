package kpaas.dogcat.domain.story.converter;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.story.dto.StoryReqDTO;
import kpaas.dogcat.domain.story.dto.StoryResDTO;
import kpaas.dogcat.domain.story.entity.Story;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StoryConverter {

    public Story toEntity(StoryReqDTO.writeStoryReqDTO dto, Member member, Pet pet){
        return Story.builder()
                .title(dto.getTitle())
                .member(member)
                .pet(pet)
                .images(dto.getImages())
                .content(dto.getContent())
                .created_at(LocalDateTime.now())
                .build();
    }

    public StoryResDTO.writeStoryResDTO toWriteStoryResDTO(Member member, Story savedStory, Pet pet) {
        return StoryResDTO.writeStoryResDTO.builder()
                .memberName((member.getNickname()))
                .storyId(savedStory.getId())
                .petDid(pet.getDid())
                .title(savedStory.getTitle())
                .images(savedStory.getImages())
                .content(savedStory.getContent())
                .build();
    }

    public StoryResDTO.StoryPreviewDTO toStoryPreviewDTO(Story story,
                                                         Long likeCount,
                                                         boolean liked,
                                                         Long commentCount) {
        return StoryResDTO.StoryPreviewDTO.builder()
                .memberName(story.getMember().getNickname())
                .storyId(story.getId())
                .petDid(story.getPet().getDid())
                .title(story.getTitle())
                .images(story.getImages())
                .content(story.getContent())
                .likeCount(likeCount)
                .liked(liked)
                .commentCount(commentCount)
                .createdAt(story.getCreated_at())
                .build();
    }
}
