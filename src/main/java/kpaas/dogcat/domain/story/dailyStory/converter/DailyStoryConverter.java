package kpaas.dogcat.domain.story.dailyStory.converter;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryReqDTO;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryResDTO;
import kpaas.dogcat.domain.story.dailyStory.entity.DailyStory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DailyStoryConverter {

    public DailyStory toDailyStoryEntity(DailyStoryReqDTO.writeStoryReqDTO dto, Member member, Pet pet, String url) {
        return DailyStory.builder()
                .title(dto.getTitle())
                .member(member)
                .pet(pet)
                .images(url)
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public DailyStoryResDTO.writeStoryResDTO toWriteStoryResDTO(Member member, DailyStory savedStory, Pet pet) {
        return DailyStoryResDTO.writeStoryResDTO.builder()
                .memberName((member.getNickname()))
                .storyId(savedStory.getId())
                .petId(pet.getId())
                .DID(pet.getDid())
                .title(savedStory.getTitle())
                .images(savedStory.getImages())
                .content(savedStory.getContent())
                .build();
    }

    // 스토리 조회
    public DailyStoryResDTO.StoryPreviewDTO toStoryPreviewDTO(DailyStory story,
                                                              Long likeCount,
                                                              boolean liked,
                                                              Long commentCount) {
        return DailyStoryResDTO.StoryPreviewDTO.builder()
                .memberName(story.getMember().getNickname())
                .storyId(story.getId())
                .petId(story.getPet().getId())
                .DID(story.getPet().getDid())
                .title(story.getTitle())
                .images(story.getImages())
                .content(story.getContent())
                .likeCount(likeCount)
                .liked(liked)
                .commentCount(commentCount)
                .createdAt(story.getCreatedAt())
                .build();
    }
}
