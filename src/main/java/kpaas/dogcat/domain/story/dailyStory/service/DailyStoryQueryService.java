package kpaas.dogcat.domain.story.dailyStory.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.story.comment.service.CommentQueryService;
import kpaas.dogcat.domain.story.dailyStory.converter.DailyStoryConverter;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryResDto;
import kpaas.dogcat.domain.story.dailyStory.entity.DailyStory;
import kpaas.dogcat.domain.story.like.service.LikeQueryService;
import kpaas.dogcat.domain.story.dailyStory.repository.DailyStoryRepository;
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
public class DailyStoryQueryService {

    private final MemberRepository memberRepository;
    private final DailyStoryRepository dailyStoryRepository;
    private final DailyStoryConverter dailyStoryConverter;
    private final LikeQueryService likeQueryService;
    private final CommentQueryService commentQueryService;

    /** 일상 일지 상세 반환 **/
    public DailyStoryResDto.StoryDetailDto getStoryDetail(Long storyId, Long memberId) {
        DailyStory story = dailyStoryRepository.findById(storyId)
                .orElseThrow(() -> new CustomException(ErrorCode.DAILYSTORY_NOTFOUND));
        Pet pet =  story.getPet();
        Member member = findMemberOrNull(memberId);

        Long likeCount = likeQueryService.getLikeCount(storyId);
        Long commentCount = commentQueryService.getCommentCount(storyId);
        boolean liked = member != null && likeQueryService.isAlreadyLike(story, member);

        return dailyStoryConverter.toStoryDetailDto(story, pet, likeCount, liked, commentCount);
    }

    /** 일상 일지 조회용 반환 **/
    public DailyStoryResDto.StoriesListDto getStories(Long cursorId, int size, Long memberId) {
        Pageable pageable = PageRequest.of(0, size);

        List<DailyStory> stories;
        if (cursorId == null) {
            stories = dailyStoryRepository.findAllByOrderByIdDesc(pageable);
        } else {
            stories = dailyStoryRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable);
        }

        Member member = findMemberOrNull(memberId);
        List<DailyStoryResDto.StoryPreviewDto> storyList = stories.stream()
                .map(story -> mapToPreviewDTO(story, member))
                .toList();
        Long nextCursor = stories.size() < size ? null : stories.get(stories.size() - 1).getId();

        return DailyStoryResDto.StoriesListDto.builder()
                .stories(storyList)
                .nextCursor(nextCursor)
                .build();
    }

    /** 일상 일지 키워드 기반 조회 **/
    public DailyStoryResDto.StoriesListDto search(String keyword, Long cursorId, int size, Long memberId) {
        Pageable pageable = PageRequest.of(0, size);
        List<DailyStory> stories;
        if (cursorId == null) {
            stories = dailyStoryRepository.findByTitleContainingFirstPage(keyword, pageable);
        } else {
            stories = dailyStoryRepository.findByTitleContainingAfterCursor(keyword, cursorId, pageable);
        }

        Member member = findMemberOrNull(memberId);
        List<DailyStoryResDto.StoryPreviewDto> storyList = stories.stream()
                .map(story -> mapToPreviewDTO(story, member))
                .toList();
        Long nextCursor = stories.size() < size ? null : stories.get(stories.size() - 1).getId();

        return DailyStoryResDto.StoriesListDto.builder()
                .stories(storyList)
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
    private DailyStoryResDto.StoryPreviewDto mapToPreviewDTO(DailyStory story, Member member) {
        Long storyId = story.getId();

        Long likeCount = likeQueryService.getLikeCount(storyId);
        Long commentCount = commentQueryService.getCommentCount(storyId);
        boolean liked = member != null && likeQueryService.isAlreadyLike(story, member);

        return dailyStoryConverter.toStoryPreviewDTO(story, likeCount, liked, commentCount);
    }

    // 홈 - 좋아요와 댓글이 가장 많은 입양 후기 3개 반환
    public List<DailyStoryResDto.StoryPreviewDto> get3PopularStories() {
        Pageable pageable = PageRequest.of(0, 3);
        List<DailyStory> stories = dailyStoryRepository.findTopPopularDailyStories(pageable);

        return stories.stream()
                .map(r -> dailyStoryConverter.toStoryPreviewDTO(
                        r, likeQueryService.getLikeCount(r.getId()),
                        false, commentQueryService.getCommentCount(r.getId())
                ))
                .toList();
    }
}
