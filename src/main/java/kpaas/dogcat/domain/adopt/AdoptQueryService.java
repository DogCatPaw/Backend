package kpaas.dogcat.domain.adopt;

import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.domain.donate.donation.service.DonationQueryService;
import kpaas.dogcat.domain.story.dailyStory.service.DailyStoryQueryService;
import kpaas.dogcat.domain.story.review.service.ReviewQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdoptQueryService {

    private final ReviewQueryService reviewQueryService;
    private final DailyStoryQueryService dailyStoryQueryService;
    private final DonationQueryService donationQueryService;
//    private final AdoptionQueryService adoptionQueryService;
    private final AdoptConverter adoptConverter;

    public AdoptResDto.HomeDto getHomeData() {
        return AdoptResDto.HomeDto.builder()
                .popularReviews(reviewQueryService.get3PopularReviews())
                .popularStories(dailyStoryQueryService.get3PopularStories())
                .closingSoonDonations(donationQueryService.get3ClosingSoonDonations())
//                .latestAdoptions(adoptionQueryService.get3LatestAdoptions())
                .build();
    }
}
