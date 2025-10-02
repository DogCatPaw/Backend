package kpaas.dogcat.domain.story.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import kpaas.dogcat.domain.story.converter.StoryConverter;
import kpaas.dogcat.domain.story.dto.StoryReqDTO;
import kpaas.dogcat.domain.story.dto.StoryResDTO;
import kpaas.dogcat.domain.story.entity.Story;
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
public class StoryCommandService {

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final StoryRepository storyRepository;
    private final StoryConverter storyConverter;

    public StoryResDTO.writeStoryResDTO createStory(Long memberId, StoryReqDTO.writeStoryReqDTO dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        Pet pet = petRepository.findById(dto.getPetDid())
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOTFOUND));

        Story story = storyConverter.toEntity(dto, member, pet);
        Story savedStory = storyRepository.save(story);

        return storyConverter.toWriteStoryResDTO(member, savedStory, pet);
    }
}
