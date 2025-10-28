package kpaas.dogcat.domain.member.service;

import kpaas.dogcat.domain.member.converter.MemberConverter;
import kpaas.dogcat.domain.member.dto.MemberResDto;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.stories.comment.service.CommentQueryService;
import kpaas.dogcat.domain.stories.like.service.LikeQueryService;
import kpaas.dogcat.domain.stories.story.entity.Story;
import kpaas.dogcat.domain.stories.story.repository.StoryRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
//import kpaas.dogcat.global.jwt.JwtUtil;
//import kpaas.dogcat.global.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;
    private final StoryRepository storyRepository;
    private final LikeQueryService likeQueryService;
    private final CommentQueryService commentQueryService;
    private final MemberConverter memberConverter;

    public Member findById(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));
    }

    public MemberResDto.StoriesListDto getStories(String walletAddress, Long cursor, int size) {
        log.info("[ 마이페이지 스토리 조회 ]");
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
