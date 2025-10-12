package kpaas.dogcat.domain.adopt.service;

import kpaas.dogcat.domain.adopt.converter.AdoptConverter;
import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.adopt.repository.AdoptRepository;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donation.enums.DonationStatus;
import kpaas.dogcat.domain.donate.donation.service.DonationQueryService;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.service.PetQueryService;
import kpaas.dogcat.domain.story.dailyStory.service.DailyStoryQueryService;
import kpaas.dogcat.domain.story.review.service.ReviewQueryService;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdoptQueryService {

    private final ReviewQueryService reviewQueryService;
    private final DailyStoryQueryService dailyStoryQueryService;
    private final DonationQueryService donationQueryService;
//    private final AdoptionQueryService adoptionQueryService;
    private final AdoptRepository adoptRepository;
    private final AdoptConverter adoptConverter;
    private final PetQueryService petQueryService;

    public AdoptResDto.HomeDto getHomeData() {
        return AdoptResDto.HomeDto.builder()
                .popularReviews(reviewQueryService.get3PopularReviews())
                .popularStories(dailyStoryQueryService.get3PopularStories())
                .closingSoonDonations(donationQueryService.get3ClosingSoonDonations())
//                .latestAdoptions(adoptionQueryService.get3LatestAdoptions())
                .build();
    }

    /** 입양 공고 상태 + 지역 + 시군구 별 조회**/
    public AdoptResDto.PreviewListDto getAdoptions(Long cursor, int size,
                                                   AdoptionStatus status,
                                                   Region region,
                                                   String district) {
        Pageable pageable = PageRequest.of(0, size);

        // 상태가 null이면 입양 가능한 상태만 조회
        if (status == null) status = AdoptionStatus.ACTIVE;

        List<Adopt> adoptions;
        if (region != null && district != null) {
            // 지역 + 시군구 + 상태
            if (cursor == null)
                adoptions = adoptRepository.findByRegionAndDistrictAndStatusOrderByIdDesc(region, district, status, pageable);
            else
                adoptions = adoptRepository.findByRegionAndDistrictAndStatusAndIdLessThanOrderByIdDesc(region, district, status, cursor, pageable);

        } else if (region != null) {
            // 지역 + 상태
            if (cursor == null)
                adoptions = adoptRepository.findByRegionAndStatusOrderByIdDesc(region, status, pageable);
            else
                adoptions = adoptRepository.findByRegionAndStatusAndIdLessThanOrderByIdDesc(region, status, cursor, pageable);
        } else {
            // 상태만
            if (cursor == null)
                adoptions = adoptRepository.findByStatusOrderByIdDesc(status, pageable);
            else
                adoptions = adoptRepository.findByStatusAndIdLessThanOrderByIdDesc(status, cursor, pageable);
        }

        List<AdoptResDto.PreviewDto> adoptionDtos = adoptions.stream()
                .map(adoption -> {
                    Pet pet = adoption.getPet();
                    String dDay = getDday(adoption);
                    return adoptConverter.toPreviewDto(dDay, pet, adoption);
                })
                .toList();

        Long nextCursor = adoptions.size() < size ? null : adoptions.get(adoptions.size() - 1).getId();

        return AdoptResDto.PreviewListDto.builder()
                .adoptions(adoptionDtos)
                .nextCursor(nextCursor)
                .build();
    }

    public AdoptResDto.DetailDto getDetails(Long adoptId){
        Adopt adopt = adoptRepository.findWithPetById(adoptId)
                .orElseThrow(() -> new CustomException(ErrorCode.ADOPTION_NOTFOUND));

        return adoptConverter.toDetailDto(adopt);
    }

    /** 디데이 계산 **/
    public String getDday(Adopt adopt){
        LocalDate today = LocalDate.now();
        long daysLeft = ChronoUnit.DAYS.between(today, adopt.getDeadline());
        String dDay;

        if (daysLeft > 0) dDay = "D-" + daysLeft;
        else if (daysLeft == 0) dDay = "D-day";
        else dDay = "마감";

        return dDay;
    }
}
