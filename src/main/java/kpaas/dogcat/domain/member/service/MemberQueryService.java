package kpaas.dogcat.domain.member.service;

import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.adopt.repository.AdoptQueryDslImpl;
import kpaas.dogcat.domain.adopt.repository.AdoptRepository;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donation.repository.DonationQueryDslImpl;
import kpaas.dogcat.domain.member.converter.MemberConverter;
import kpaas.dogcat.domain.member.dto.MemberResDto;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.stories.comment.service.CommentQueryService;
import kpaas.dogcat.domain.stories.dailyStory.entity.DailyStory;
import kpaas.dogcat.domain.stories.like.service.LikeQueryService;
import kpaas.dogcat.domain.stories.review.entity.Review;
import kpaas.dogcat.domain.stories.story.entity.Story;
import kpaas.dogcat.domain.stories.story.repository.StoryRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
//import kpaas.dogcat.global.jwt.JwtUtil;
//import kpaas.dogcat.global.redis.service.RedisService;
import kpaas.dogcat.global.enums.PostType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final AdoptRepository adoptRepository;
    private final AdoptQueryDslImpl adoptQueryDslImpl;
    private final DonationQueryDslImpl donationQueryDslImpl;


    public Member findById(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));
    }

    public MemberResDto.ProfileResDto getProfile(String walletAddress) {
        log.info("[ 마이페이지 상단 프로필 조회 ]");
        Member member = findById(walletAddress);

        return memberConverter.toProfileResDto(member);
    }

    public MemberResDto.StoriesListDto getStories(String walletAddress, Long cursor, int size, PostType type) {
        log.info("[ 마이페이지 글 조회 - 타입: {} ]", type);

        List<MemberResDto.StoryDto> dtos = new ArrayList<>();

        if (type == null) {
            log.error("[ERROR] type 값이 null 입니다.");
            throw new CustomException(ErrorCode.BAD_REQUEST_400);
        }

        switch (type) {
            case DAILY, REVIEW -> {
                List<Story> stories = storyRepository.findStoriesByWalletAddress(walletAddress, cursor, size, type);
                dtos = stories.stream()
                        .map(this::mapToPreviewDto)
                        .toList();
            }
            case ADOPTION -> {
                List<Adopt> adopts = adoptQueryDslImpl.findAdoptsByWalletAddress(walletAddress, cursor, size);
                dtos = adopts.stream()
                        .map(memberConverter::toStoryDtoFromAdopt)
                        .toList();
            }
            case DONATION -> {
                List<Donation> donations = donationQueryDslImpl.findDonationsByWalletAddress(walletAddress, cursor, size);
                dtos = donations.stream()
                        .map(memberConverter::toStoryDtoFromDonation)
                        .toList();
            }
            default -> log.warn("[WARN] 지정되지 않은 PostType 요청: {}", type);
        }

        Long nextCursor = dtos.size() < size ? null : dtos.get(dtos.size() - 1).getPostId();

        return MemberResDto.StoriesListDto.builder()
                .stories(dtos)
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
