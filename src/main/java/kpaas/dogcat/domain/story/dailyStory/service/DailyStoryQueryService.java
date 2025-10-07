package kpaas.dogcat.domain.story.dailyStory.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.story.Story;
import kpaas.dogcat.domain.story.comment.service.CommentQueryService;
import kpaas.dogcat.domain.story.dailyStory.converter.DailyStoryConverter;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryResDTO;
import kpaas.dogcat.domain.story.dailyStory.entity.DailyStory;
import kpaas.dogcat.domain.story.like.service.LikeQueryService;
import kpaas.dogcat.domain.story.dailyStory.repository.DailyStoryRepository;
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

    public DailyStoryResDTO.StoryPreviewDTO getStory(Long storyId, Long memberId) {
        DailyStory story = dailyStoryRepository.findById(storyId)
                .orElseThrow(() -> new CustomException(ErrorCode.DAILYSTORY_NOTFOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        return mapToPreviewDTO(story, member);
    }

    public DailyStoryResDTO.StoriesListDTO getStories(Long cursorId, int size, Long memberId) {
        Pageable pageable = PageRequest.of(0, size);

        List<DailyStory> stories;
        if (cursorId == null) {
            stories = dailyStoryRepository.findAllByOrderByIdDesc(pageable);
        } else {
            stories = dailyStoryRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        List<DailyStoryResDTO.StoryPreviewDTO> storyList = stories.stream()
                .map(story -> mapToPreviewDTO(story, member))
                .toList();

        Long nextCursor = stories.size() < size ? null : stories.get(stories.size() - 1).getId();

        return DailyStoryResDTO.StoriesListDTO.builder()
                .stories(storyList)
                .nextCursor(nextCursor)
                .build();
    }

    public DailyStoryResDTO.StoriesListDTO search(String keyword, Long cursorId, int size) {
        Pageable pageable = PageRequest.of(0, size);

        List<DailyStory> stories;
        if (cursorId == null) {
            stories = dailyStoryRepository.findByTitleContainingFirstPage(keyword, pageable);
        } else {
            stories = dailyStoryRepository.findByTitleContainingAfterCursor(keyword, cursorId, pageable);
        }

        Member member = null;
        List<DailyStoryResDTO.StoryPreviewDTO> storyList = stories.stream()
                .map(story -> mapToPreviewDTO(story, member))
                .toList();

        Long nextCursor = stories.size() < size ? null : stories.get(stories.size() - 1).getId();

        return DailyStoryResDTO.StoriesListDTO.builder()
                .stories(storyList)
                .nextCursor(nextCursor)
                .build();
    }

    /** 스토리 하나조회, 전체 조회, 제목 검색
     * 공통 변환 메서드 */
    private DailyStoryResDTO.StoryPreviewDTO mapToPreviewDTO(DailyStory story, Member member) {
        Long storyId = story.getId();

        Long likeCount = likeQueryService.getLikeCount(storyId);
        Long commentCount = commentQueryService.getCommentCount(storyId);
        boolean liked = member != null && likeQueryService.isAlreadyLike(story, member);

        return dailyStoryConverter.toStoryPreviewDTO(story, likeCount, liked, commentCount);
    }
}
