package kpaas.dogcat.domain.adopt.dto;

import kpaas.dogcat.domain.donate.donation.dto.DonationResDto;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryResDto;
import kpaas.dogcat.domain.story.review.dto.ReviewResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class AdoptResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HomeDto {
        private List<ReviewResDto.ReviewDto> popularReviews;           // 인기 후기 3개
        private List<DailyStoryResDto.StoryPreviewDto> popularStories; // 인기 일상 일지 3개
        private List<DonationResDto.PreviewDto> closingSoonDonations;     // 마감 임박 후원공고 3개
//        private List<AdoptionResDTO.SimpleDto> latestAdoptions;        // 최신 입양공고 3개
    }
}
