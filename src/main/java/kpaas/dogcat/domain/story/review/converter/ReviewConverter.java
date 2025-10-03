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

    public ReviewResDTO.WriteReviewResDTO toWriteReviewResDTO(Member member, Review savedReview, Pet pet) {
        return ReviewResDTO.WriteReviewResDTO.builder()
                .memberName((member.getNickname()))
                .storyId(savedReview.getId())
                .petDid(pet.getDid())
                .title(savedReview.getTitle())
                .images(savedReview.getImages())
                .content(savedReview.getContent())
                .adoptionAgency(savedReview.getAdoptionAgency())
                .adoptionDate(savedReview.getAdoptionDate())
                .build();
    }

    public ReviewResDTO.ReviewDTO toReviewPreviewDTO(Review review,
                                                     Long likeCount,
                                                     boolean liked,
                                                     Long commentCount) {
        return ReviewResDTO.ReviewDTO.builder()
                .memberName(review.getMember().getNickname())
                .storyId(review.getId())
                .petDid(review.getPet().getDid())
                .title(review.getTitle())
                .images(review.getImages())
                .content(review.getContent())
                .likeCount(likeCount)
                .liked(liked)
                .commentCount(commentCount)
                .adoptionAgency(review.getAdoptionAgency())
                .adoptionDate(review.getAdoptionDate())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
