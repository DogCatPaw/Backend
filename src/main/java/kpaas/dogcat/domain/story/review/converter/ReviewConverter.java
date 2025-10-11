package kpaas.dogcat.domain.story.review.converter;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.story.review.dto.ReviewReqDTO;
import kpaas.dogcat.domain.story.review.dto.ReviewResDTO;
import kpaas.dogcat.domain.story.review.entity.Review;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewConverter {

    public Review toReviewEntity(ReviewReqDTO.WriteReviewDTO dto, Member member, Pet pet, String url) {
        return Review.builder()
                .title(dto.getTitle())
                .member(member)
                .pet(pet)
                .images(url)
                .content(dto.getContent())
                .adoptionAgency(dto.getAdoptionAgency())
                .adoptionDate(dto.getAdoptionDate())    //입양일
                .createdAt(LocalDateTime.now())         //후기 작성일
                .build();
    }

    public ReviewResDTO.WriteReviewResDto toWriteReviewResDTO(Member member, Review savedReview, Pet pet) {
        return ReviewResDTO.WriteReviewResDto.builder()
                .memberName((member.getNickname()))
                .storyId(savedReview.getId())
                .petId(pet.getId())
                .build();
    }


    public ReviewResDTO.ReviewDetailDto toReviewDetailDTO(Review review, Pet pet, Long likeCount, boolean liked, Long commentCount) {
        return ReviewResDTO.ReviewDetailDto.builder()
                .profileUrl(review.getMember().getProfileUrl())
                .memberName(review.getMember().getNickname())
                .petId(pet.getId())
                .DID(pet.getDid())
                .title(review.getTitle())
                .content(review.getContent())
                .images(review.getImages())
                .breed(pet.getBreed())
                .petName(pet.getPetName())
                .likeCount(likeCount)
                .commentCount(commentCount)
                .liked(liked)
                .adoptionAgency(review.getAdoptionAgency())
                .adoptionDate(review.getAdoptionDate())
                .createdAt(review.getCreatedAt())
                .build();
    }


    public ReviewResDTO.ReviewDto toReviewPreviewDTO(Review review,
                                                     Long likeCount,
                                                     boolean liked,
                                                     Long commentCount) {
        return ReviewResDTO.ReviewDto.builder()
                .profileUrl(review.getMember().getProfileUrl())
                .memberName(review.getMember().getNickname())
                .title(review.getTitle())
                .images(review.getImages())
                .petName(review.getPet().getPetName())
                .breed(review.getPet().getBreed())
                .content(review.getContent())
                .likeCount(likeCount)
                .liked(liked)
                .commentCount(commentCount)
                .build();
    }
}
