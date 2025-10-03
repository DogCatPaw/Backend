package kpaas.dogcat.domain.story.dailyStory.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
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

        Long likeCount = likeQueryService.getLikeCount(storyId);
        boolean liked = likeQueryService.isAlreadyLike(story, member);
        Long commentCount = commentQueryService.getCommentCount(storyId);

        return dailyStoryConverter.toStoryPreviewDTO(story, likeCount, liked, commentCount);
    }

    public DailyStoryResDTO.StoriesListDTO getStories(Long cursorId, int size, Long memberId) {
        Pageable pageable = PageRequest.of(0, size);

        List<DailyStory> stories;
        if (cursorId == null) {
            // 첫 페이지 요청 (cursor 없음 → 최신순으로 size만큼)
            stories = dailyStoryRepository.findAllByOrderByIdDesc(pageable);
        } else {
            // cursorId 이전 데이터 조회
            stories = dailyStoryRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        List<DailyStoryResDTO.StoryPreviewDTO> storyPreviews = stories.stream()
                .map(story -> dailyStoryConverter.toStoryPreviewDTO(
                        story,
                        likeQueryService.getLikeCount(story.getId()),
                        likeQueryService.isAlreadyLike(story, member),
                        commentQueryService.getCommentCount(story.getId())
                ))
                .toList();

        Long nextCursor = storyPreviews.isEmpty() ? null : storyPreviews.get(storyPreviews.size() - 1).getStoryId();

        return DailyStoryResDTO.StoriesListDTO.builder()
                .stories(storyPreviews)
                .nextCursor(nextCursor)
                .build();
    }
}
