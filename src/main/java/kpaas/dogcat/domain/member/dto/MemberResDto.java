package kpaas.dogcat.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class MemberResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SignupResDto {
        private String walletAddress;
        private String nickname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoryDto {
        private Long storyId;
        private String title;
        private Long comments;
        private Long likes;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoriesListDto {
        private List<StoryDto> stories;
        private Long nextCursor;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProfileResDto {
        private String walletAddress;
        private String profileImage;
        private String username;
        private String nickname;
        private String phoneNumber;
        private LocalDateTime createdAt;
    }
}
