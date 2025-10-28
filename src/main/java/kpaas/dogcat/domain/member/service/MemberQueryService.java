package kpaas.dogcat.domain.member.service;

import kpaas.dogcat.domain.member.converter.MemberConverter;
import kpaas.dogcat.domain.member.dto.MemberResDto;
import kpaas.dogcat.domain.stories.story.entity.Story;
import kpaas.dogcat.domain.stories.story.repository.StoryRepository;
import kpaas.dogcat.domain.stories.comment.service.CommentQueryService;
import kpaas.dogcat.domain.stories.like.service.LikeQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final StoryRepository storyRepository;
    private final LikeQueryService likeQueryService;
    private final CommentQueryService commentQueryService;
    private final MemberConverter memberConverter;

    public MemberResDto.StoriesListDto getStories(String walletAddress, Long cursor, int size) {

        List<Story> stories = storyRepository.findStoriesByWalletAddress(walletAddress, cursor, size);

        List<MemberResDto.StoryDto> storyDtos = stories.stream()
                .map(story -> mapToPreviewDto(story))
                .toList();
        Long nextCursor = stories.size() < size ? null : stories.get(stories.size() - 1).getId();

        return MemberResDto.StoriesListDto.builder()
                .stories(storyDtos)
                .nextCursor(nextCursor)
                .build();
    }

    private MemberResDto.StoryDto mapToPreviewDto(Story story) {
        Long storyId = story.getId();
        Long likeCount = likeQueryService.getLikeCount(storyId);
        Long commentCount = commentQueryService.getCommentCount(storyId);
        return memberConverter.toStoryDto(story, likeCount, commentCount);
    }
}
