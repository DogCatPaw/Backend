package kpaas.dogcat.domain.story.review.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import kpaas.dogcat.domain.story.dailyStory.entity.DailyStory;
import kpaas.dogcat.domain.story.review.converter.ReviewConverter;
import kpaas.dogcat.domain.story.review.dto.ReviewReqDTO;
import kpaas.dogcat.domain.story.review.dto.ReviewResDto;
import kpaas.dogcat.domain.story.review.entity.Review;
import kpaas.dogcat.domain.story.review.repository.ReviewRepository;
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
public class ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final PetRepository petRepository;
    private final ReviewConverter reviewConverter;
    private final ObjectStorageUtil objectStorageUtil;
    private final AuthCommandService authCommandService;

    public ReviewResDto.WriteReviewResDto writeReview(
            String memberId, ReviewReqDTO.WriteReviewDTO dto) {
        log.info("[ 입양 후기 작성하기 ]");
        Member member = authCommandService.findById(memberId);
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOTFOUND));

        Review review = reviewConverter.toReviewEntity(dto, member, pet);
        Review savedReview = reviewRepository.save(review);

        return reviewConverter.toWriteReviewResDTO(member, savedReview, pet);
    }

    public void delete(Long storyId, String walletAddress) {
        Member member = authCommandService.findById(walletAddress);
        Review story = reviewRepository.findById(storyId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOTFOUND));

        boolean isStoryWriter = story.getMember().equals(member);
        if (!isStoryWriter) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_401);
        }
        reviewRepository.delete(story);
        log.info("[ 입양 후기 삭제 완료 - 스토리: {}, 작성자: {}", storyId, walletAddress);
    }
}
