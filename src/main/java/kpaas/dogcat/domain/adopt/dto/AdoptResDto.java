package kpaas.dogcat.domain.adopt.dto;

import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.donate.donation.dto.DonationResDto;
import kpaas.dogcat.domain.pet.enums.Breed;
import kpaas.dogcat.domain.pet.enums.Gender;
import kpaas.dogcat.domain.stories.dailyStory.dto.DailyStoryResDto;
import kpaas.dogcat.domain.stories.review.dto.ReviewResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AdoptResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HomeDto {
        private List<AdoptResDto.AdoptPreviewDto> latestAdoptions;        // 최신 입양공고 3개
        private List<DonationResDto.DonationPreviewDto> closingSoonDonations;  // 마감 임박 후원공고 3개
        private List<ReviewResDto.ReviewDto> popularReviews;           // 인기 후기 3개
        private List<DailyStoryResDto.StoryPreviewDto> popularStories; // 인기 일상 일지 3개
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RegisterDto {
        private Long adoptId;
        private String targetId;
        private String adoptTitle;
        private Long petId;
        private String images;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AdoptPreviewDto {
        private Long adoptId;
        private String thumbnail;
        private String title;
        private Breed breed;
        private String did;
        private Region region;
        private String district;
        private String shelterName;
        //        private String contact;
        private String dDay;
        private AdoptionStatus status;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdoptPreviewListDto {
        private List<AdoptPreviewDto> adoptions;
        private Long nextCursor;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AdoptDetailDto {
        private String title;
        private String content;
        private String images;

        private String did;
        private String petProfile;
        private String petName;
        private int old;
        private int weight;
        private String color;
        private boolean isNeutral;      //중성화
        private String specifics;       //특이사항, 메모
        private Gender gender;
        private Breed breed;

        private Region region;          // 광역시·도
        private String district;        // 군·구 (ex. "강남구")
        private String shelterName;
//        private String contact;
        private LocalDate deadline;
        private AdoptionStatus status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DelegateDto {
        private String adoptWriterId;
        private String adopterId;
        private AdoptionStatus status;
        private LocalDateTime appliedAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MyAdoptionDto {
        private Long adoptId;
        private String petName;
        private Breed breed;
        private Region region;
        private String district;
        private String shelterName;
        private AdoptionStatus status;
        private LocalDateTime appliedAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MyAdoptionListDto {
        List<MyAdoptionDto> adoptions;
        private Long cursor;
    }
}
