package kpaas.dogcat.domain.story.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.story.converter.StoryConverter;
import kpaas.dogcat.domain.story.dto.StoryResDTO;
import kpaas.dogcat.domain.story.entity.Story;
import kpaas.dogcat.domain.story.repository.StoryRepository;
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
public class StoryQueryService {

    private final StoryRepository storyRepository;
    private final MemberRepository memberRepository;
    private final StoryConverter storyConverter;
    private final LikeQueryService likeQueryService;
    private final LikeCommandService likeCommandService;
    private final CommentQueryService commentQueryService;

    public StoryResDTO.StoryPreviewDTO getStory(Long storyId, Long memberId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORY_NOTFOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        Long likeCount = likeQueryService.getLikeCount(storyId);
        boolean liked = likeQueryService.isAlreadyLike(story, member);
        Long commentCount = commentQueryService.getCommentCount(storyId);

        return storyConverter.toStoryPreviewDTO(story, likeCount, liked, commentCount);
    }

    public StoryResDTO.StoriesListDTO getStories(Long cursorId, int size, Long memberId) {
        Pageable pageable = PageRequest.of(0, size);

        List<Story> stories;
        if (cursorId == null) {
            // 첫 페이지 요청 (cursor 없음 → 최신순으로 size만큼)
            stories = storyRepository.findAllByOrderByIdDesc(pageable);
        } else {
            // cursorId 이전 데이터 조회
            stories = storyRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        List<StoryResDTO.StoryPreviewDTO> storyPreviews = stories.stream()
                .map(story -> storyConverter.toStoryPreviewDTO(
                        story,
                        likeQueryService.getLikeCount(story.getId()),
                        likeQueryService.isAlreadyLike(story, member),
                        commentQueryService.getCommentCount(story.getId())
                ))
                .toList();

        Long nextCursor = storyPreviews.isEmpty() ? null : storyPreviews.get(storyPreviews.size() - 1).getStoryId();

        return StoryResDTO.StoriesListDTO.builder()
                .stories(storyPreviews)
                .nextCursor(nextCursor)
                .build();
    }
}
