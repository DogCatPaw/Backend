package kpaas.dogcat.domain.member.converter;

import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.member.dto.MemberReqDto;
import kpaas.dogcat.domain.member.dto.MemberResDto;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.stories.story.entity.Story;
import kpaas.dogcat.global.enums.PostType;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member toSignupEntity(MemberReqDto.SignupReqDto dto) {
        return Member.builder()
                .id(dto.getWalletAddress())
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .gender(dto.getGender())
                .old(dto.getOld())
                .phoneNumber(String.valueOf(dto.getPhoneNumber()))
                .profileUrl(dto.getProfileUrl())
                .role(dto.getRole())
                .build();
    }

    public MemberResDto.SignupResDto toSignupResDto(Member member) {
        return MemberResDto.SignupResDto.builder()
                .walletAddress(member.getId())
                .nickname(member.getNickname())
                .build();
    }

    public MemberResDto.ProfileResDto toProfileResDto(Member member) {
        return MemberResDto.ProfileResDto.builder()
                .walletAddress(member.getId())
                .profileImage(member.getProfileUrl())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public MemberResDto.StoryDto toStoryDto(Story story,
                                            Long likeCount,
                                            Long commentCount) {
        return MemberResDto.StoryDto.builder()
                .postId(story.getId())
                .title(story.getTitle())
                .likes(likeCount)
                .comments(commentCount)
                .type(story.getPostType())
                .build();
    }

    public MemberResDto.StoryDto toStoryDtoFromAdopt(Adopt adopt) {
        return MemberResDto.StoryDto.builder()
                .postId(adopt.getId())
                .title(adopt.getTitle())
                .type(PostType.ADOPTION)
                .build();
    }

    public MemberResDto.StoryDto toStoryDtoFromDonation(Donation donation) {
        return MemberResDto.StoryDto.builder()
                .postId(donation.getId())
                .title(donation.getTitle())
                .type(PostType.DONATION)
                .build();
    }
}
