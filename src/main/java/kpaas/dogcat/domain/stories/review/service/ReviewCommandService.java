package kpaas.dogcat.domain.stories.review.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.service.MemberQueryService;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import kpaas.dogcat.domain.stories.review.converter.ReviewConverter;
import kpaas.dogcat.domain.stories.review.dto.ReviewReqDTO;
import kpaas.dogcat.domain.stories.review.dto.ReviewResDto;
import kpaas.dogcat.domain.stories.review.entity.Review;
import kpaas.dogcat.domain.stories.review.repository.ReviewRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import kpaas.dogcat.global.objectStorage.ObjectStorageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final PetRepository petRepository;
    private final ReviewConverter reviewConverter;
    private final MemberQueryService memberQueryService;

    public ReviewResDto.WriteReviewResDto writeReview(
            String memberId, ReviewReqDTO.WriteReviewDTO dto) {
        log.info("[ 입양 후기 작성하기 ]");
        Member member = memberQueryService.findById(memberId);
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOTFOUND));

        Review review = reviewConverter.toReviewEntity(dto, member, pet);
        Review savedReview = reviewRepository.save(review);

        return reviewConverter.toWriteReviewResDTO(member, savedReview, pet);
    }

    public void delete(Long storyId, String walletAddress) {
        Member member = memberQueryService.findById(walletAddress);
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
