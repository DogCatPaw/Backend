package kpaas.dogcat.domain.member.converter;

import kpaas.dogcat.domain.member.dto.MemberReqDto;
import kpaas.dogcat.domain.member.dto.MemberResDto;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.stories.story.entity.Story;
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

    public MemberResDto.StoryDto toStoryDto(Story story,
                                            Long likeCount,
                                            Long commentCount) {
        return MemberResDto.StoryDto.builder()
                .storyId(story.getId())
                .title(story.getTitle())
                .likes(likeCount)
                .comments(commentCount)
                .build();
    }
}
