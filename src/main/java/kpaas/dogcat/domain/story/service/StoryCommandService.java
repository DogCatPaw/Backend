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
import kpaas.dogcat.global.objectStorage.ObjectStorageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoryCommandService {

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final StoryRepository storyRepository;
    private final StoryConverter storyConverter;
    private final ObjectStorageUtil objectStorageUtil;

    public StoryResDTO.writeStoryResDTO createStory(Long memberId, StoryReqDTO.writeStoryReqDTO dto, MultipartFile image) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        Pet pet = petRepository.findById(dto.getPetDid())
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOTFOUND));

//        if (image == null || image.isEmpty()) {
//            throw new CustomException(ErrorCode.IMAGE_REQUIRED);
//        }
        String url = objectStorageUtil.upload(image);
        Story story = storyConverter.toEntity(dto, member, pet, url);
        Story savedStory = storyRepository.save(story);

        return storyConverter.toWriteStoryResDTO(member, savedStory, pet);
    }
}
