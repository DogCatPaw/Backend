package kpaas.dogcat.domain.adopt.service;

import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.domain.donate.donation.service.DonationQueryService;
import kpaas.dogcat.domain.story.dailyStory.service.DailyStoryQueryService;
import kpaas.dogcat.domain.story.review.service.ReviewQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdoptQueryService {

    private final ReviewQueryService reviewQueryService;
    private final DailyStoryQueryService dailyStoryQueryService;
    private final DonationQueryService donationQueryService;
//    private final AdoptionQueryService adoptionQueryService;

    public AdoptResDto.HomeDto getHomeData() {
        return AdoptResDto.HomeDto.builder()
                .popularReviews(reviewQueryService.get3PopularReviews())
                .popularStories(dailyStoryQueryService.get3PopularStories())
                .closingSoonDonations(donationQueryService.get3ClosingSoonDonations())
//                .latestAdoptions(adoptionQueryService.get3LatestAdoptions())
                .build();
    }
}
