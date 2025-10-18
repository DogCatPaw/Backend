package kpaas.dogcat.domain.story.dailyStory.converter;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryReqDto;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryResDto;
import kpaas.dogcat.domain.story.dailyStory.entity.DailyStory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DailyStoryConverter {

    public DailyStory toDailyStoryEntity(DailyStoryReqDto.WriteStoryReqDto dto, Member member, Pet pet) {
        return DailyStory.builder()
                .title(dto.getTitle())
                .member(member)
                .pet(pet)
                .images(dto.getImages())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public DailyStoryResDto.WriteStoryResDto toWriteStoryResDto(Member member, DailyStory savedStory, Pet pet) {
        return DailyStoryResDto.WriteStoryResDto.builder()
                .memberName((member.getNickname()))
                .storyId(savedStory.getId())
                .petId(pet.getId())
                .DID(pet.getDid())
                .title(savedStory.getTitle())
                .images(savedStory.getImages())
                .content(savedStory.getContent())
                .build();
    }

    // 스토리 하나의 상세 조회용
    public DailyStoryResDto.StoryDetailDto toStoryDetailDto(DailyStory story, Pet pet, Long likeCount, boolean liked, Long commentCount) {
        return DailyStoryResDto.StoryDetailDto.builder()
                .profileUrl(story.getMember().getProfileUrl())
                .memberName(story.getMember().getNickname())
                .petId(pet.getId())
                .DID(pet.getDid())
                .title(story.getTitle())
                .content(story.getContent())
                .images(story.getImages())
                .breed(pet.getBreed())
                .petName(pet.getPetName())
                .likeCount(likeCount)
                .commentCount(commentCount)
                .liked(liked)
                .createdAt(story.getCreatedAt())
                .build();
    }

    // 스토리 목록 조회용
    public DailyStoryResDto.StoryPreviewDto toStoryPreviewDTO(DailyStory story,
                                                              String thumbnailUrl,
                                                              Long likeCount,
                                                              boolean liked,
                                                              Long commentCount) {
        return DailyStoryResDto.StoryPreviewDto.builder()
                .storyId(story.getId())
                .profileUrl(story.getMember().getProfileUrl())
                .memberName(story.getMember().getNickname())
                .title(story.getTitle())
                .images(thumbnailUrl)
                .petName(story.getPet().getPetName())
                .breed(story.getPet().getBreed())
                .content(story.getContent())
                .likeCount(likeCount)
                .liked(liked)
                .commentCount(commentCount)
                .build();
    }
}
