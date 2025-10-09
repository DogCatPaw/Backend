package kpaas.dogcat.domain.story.dailyStory.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import kpaas.dogcat.domain.story.dailyStory.converter.DailyStoryConverter;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryReqDTO;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryResDTO;
import kpaas.dogcat.domain.story.dailyStory.entity.DailyStory;
import kpaas.dogcat.domain.story.dailyStory.repository.DailyStoryRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import kpaas.dogcat.global.objectStorage.ObjectStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DailyStoryCommandService {

    private final DailyStoryRepository dailyStoryRepository;
    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final DailyStoryConverter dailyStoryConverter;
    private final ObjectStorageUtil objectStorageUtil;

    public DailyStoryResDTO.writeStoryResDTO writeDailyStory(Long memberId, DailyStoryReqDTO.writeStoryReqDTO dto, MultipartFile image) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOTFOUND));

//        if (image == null || image.isEmpty()) {
//            throw new CustomException(ErrorCode.IMAGE_REQUIRED);
//        }
        String url = objectStorageUtil.upload(image);
        DailyStory story = dailyStoryConverter.toDailyStoryEntity(dto, member, pet, url);
        DailyStory savedStory = dailyStoryRepository.save(story);

        return dailyStoryConverter.toWriteStoryResDTO(member, savedStory, pet);
    }
}
