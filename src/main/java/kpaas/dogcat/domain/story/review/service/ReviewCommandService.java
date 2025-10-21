package kpaas.dogcat.domain.story.review.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
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
    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final ReviewConverter reviewConverter;
    private final ObjectStorageUtil objectStorageUtil;

    public ReviewResDto.WriteReviewResDto writeReview(
            String memberId, ReviewReqDTO.WriteReviewDTO dto) {
        log.info("[ 입양 후기 작성하기 ]");
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOTFOUND));

//        List<String> imageUrls = objectStorageUtil.uploadMultiple(images);
//        String joinedUrls = String.join(",", imageUrls);

        Review review = reviewConverter.toReviewEntity(dto, member, pet);
        Review savedReview = reviewRepository.save(review);

        return reviewConverter.toWriteReviewResDTO(member, savedReview, pet);
    }
}
