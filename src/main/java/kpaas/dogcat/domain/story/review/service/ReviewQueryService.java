package kpaas.dogcat.domain.story.review.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
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

    public ReviewResDTO.ReviewDTO getReview(Long reviewId, Long memberId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOTFOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        Long likeCount = likeQueryService.getLikeCount(reviewId);
        boolean liked = likeQueryService.isAlreadyLike(review, member);
        Long commentCount = commentQueryService.getCommentCount(reviewId);

        return reviewConverter.toReviewPreviewDTO(review, likeCount, liked, commentCount);
    }

    public ReviewResDTO.ReviewListDTO getReviews(Long cursorId, int size, Long memberId) {
        Pageable pageable = PageRequest.of(0, size);

        List<Review> stories;
        if (cursorId == null) {
            // 첫 페이지 요청 (cursor 없음 → 최신순으로 size만큼)
            stories = reviewRepository.findAllByOrderByIdDesc(pageable);
        } else {
            // cursorId 이전 데이터 조회
            stories = reviewRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        List<ReviewResDTO.ReviewDTO> reviewPreviews = stories.stream()
                .map(story -> reviewConverter.toReviewPreviewDTO(
                        story,
                        likeQueryService.getLikeCount(story.getId()),
                        likeQueryService.isAlreadyLike(story, member),
                        commentQueryService.getCommentCount(story.getId())
                ))
                .toList();

        Long nextCursor = reviewPreviews.isEmpty() ? null : reviewPreviews.get(reviewPreviews.size() - 1).getStoryId();

        return ReviewResDTO.ReviewListDTO.builder()
                .reviews(reviewPreviews)
                .nextCursor(nextCursor)
                .build();
    }
}
