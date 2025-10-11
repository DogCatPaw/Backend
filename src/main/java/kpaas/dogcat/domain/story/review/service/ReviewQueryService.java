package kpaas.dogcat.domain.story.review.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.story.comment.service.CommentQueryService;
import kpaas.dogcat.domain.story.like.service.LikeQueryService;
import kpaas.dogcat.domain.story.review.converter.ReviewConverter;
import kpaas.dogcat.domain.story.review.repository.ReviewRepository;
import kpaas.dogcat.domain.story.review.dto.ReviewResDTO;
import kpaas.dogcat.domain.story.review.entity.Review;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewQueryService {
    
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;
    private final LikeQueryService likeQueryService;
    private final CommentQueryService commentQueryService;

    /** 입양 후기 상세 반환*/
    public ReviewResDTO.ReviewDetailDto getReviewDetail(Long reviewId, Long memberId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOTFOUND));
        Pet pet =  review.getPet();
        Member member = findMemberOrNull(memberId);

        Long likeCount = likeQueryService.getLikeCount(reviewId);
        Long commentCount = commentQueryService.getCommentCount(reviewId);
        boolean liked = member != null && likeQueryService.isAlreadyLike(review, member);

        return reviewConverter.toReviewDetailDTO(review, pet, likeCount, liked, commentCount);
    }

    /** 입양 후기 조회용 반환 **/
    public ReviewResDTO.ReviewListDto getReviews(Long cursorId, int size, Long memberId) {
        Pageable pageable = PageRequest.of(0, size);

        List<Review> reviews;
        if (cursorId == null) {
            reviews = reviewRepository.findAllByOrderByIdDesc(pageable);
        } else {
            reviews = reviewRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable);
        }

        Member member = findMemberOrNull(memberId);
        List<ReviewResDTO.ReviewDto> reviewList = reviews.stream()
                .map(review -> mapToPreviewDTO(review, member))
                .toList();
        Long nextCursor = reviews.size() < size ? null : reviews.get(reviews.size() - 1).getId();
//        Long nextCursor = reviewList.isEmpty() ? null : reviewList.get(reviewList.size() - 1).getId();

        return ReviewResDTO.ReviewListDto.builder()
                .reviews(reviewList)
                .nextCursor(nextCursor)
                .build();
    }

    /** 입양 후기 키워드 찾기 **/
    public ReviewResDTO.ReviewListDto search(String keyword, Long cursorId, int size, Long memberId) {
        Pageable pageable = PageRequest.of(0, size);
        List<Review> reviews;
        if (cursorId == null) {
            reviews = reviewRepository.findByTitleContainingFirstPage(keyword, pageable);
        } else {
            reviews = reviewRepository.findByTitleContainingAfterCursor(keyword, cursorId, pageable);
        }

        Member member = findMemberOrNull(memberId);
        List<ReviewResDTO.ReviewDto> reviewList = reviews.stream()
                .map(review -> mapToPreviewDTO(review, member))
                .toList();
        Long nextCursor = reviews.size() < size ? null : reviews.get(reviews.size() - 1).getId();

        return ReviewResDTO.ReviewListDto.builder()
                .reviews(reviewList)
                .nextCursor(nextCursor)
                .build();
    }


    // 멤버가 null이면 좋아요 false로 조회가 가능하게끔
    private Member findMemberOrNull(Long memberId) {
        if (memberId == null) return null;
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));
    }

    /** 스토리 하나조회, 전체 조회, 제목 검색
     * 공통 변환 메서드 */
    private ReviewResDTO.ReviewDto mapToPreviewDTO(Review review, Member member) {
        Long storyId = review.getId();

        Long likeCount = likeQueryService.getLikeCount(storyId);
        Long commentCount = commentQueryService.getCommentCount(storyId);
        boolean liked = member != null && likeQueryService.isAlreadyLike(review, member);

        return reviewConverter.toReviewPreviewDTO(review, likeCount, liked, commentCount);
    }

    // 홈 - 좋아요와 댓글이 가장 많은 입양 후기 3개 반환
    public List<ReviewResDTO.ReviewDto> get3PopularReviews() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Review> reviews = reviewRepository.findTopPopularReview(pageable);

        return reviews.stream()
                .map(r -> reviewConverter.toReviewPreviewDTO(
                        r, likeQueryService.getLikeCount(r.getId()),
                        false, commentQueryService.getCommentCount(r.getId())
                ))
                .toList();
    }
}
