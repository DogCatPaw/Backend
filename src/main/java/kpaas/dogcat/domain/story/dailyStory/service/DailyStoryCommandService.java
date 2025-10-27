package kpaas.dogcat.domain.story.dailyStory.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import kpaas.dogcat.domain.story.comment.entity.Comment;
import kpaas.dogcat.domain.story.dailyStory.converter.DailyStoryConverter;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryReqDto;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryResDto;
import kpaas.dogcat.domain.story.dailyStory.entity.DailyStory;
import kpaas.dogcat.domain.story.dailyStory.repository.DailyStoryRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import kpaas.dogcat.global.objectStorage.ObjectStorageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyStoryCommandService {

    private final DailyStoryRepository dailyStoryRepository;
    private final AuthCommandService authCommandService;
    private final PetRepository petRepository;
    private final DailyStoryConverter dailyStoryConverter;
    private final ObjectStorageUtil objectStorageUtil;

    public DailyStoryResDto.WriteStoryResDto writeDailyStory(
            String memberId, DailyStoryReqDto.WriteStoryReqDto dto) {
        log.info("[ 일상 일지 작성하기 ]");
        Member member = authCommandService.findById(memberId);
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOTFOUND));

        DailyStory story = dailyStoryConverter.toDailyStoryEntity(dto, member, pet);
        DailyStory savedStory = dailyStoryRepository.save(story);

        return dailyStoryConverter.toWriteStoryResDto(member, savedStory, pet);
    }

    public void delete(Long storyId, String walletAddress) {
        Member member = authCommandService.findById(walletAddress);
        DailyStory story = dailyStoryRepository.findById(storyId)
                .orElseThrow(() -> new CustomException(ErrorCode.DAILYSTORY_NOTFOUND));

        boolean isStoryWriter = story.getMember().equals(member);
        if (!isStoryWriter) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_401);
        }
        dailyStoryRepository.delete(story);
        log.info("[ 스토리 삭제 완료 - 스토리: {}, 작성자: {}", storyId, walletAddress);
    }
}
