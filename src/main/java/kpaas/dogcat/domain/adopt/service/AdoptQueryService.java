package kpaas.dogcat.domain.adopt.service;

import kpaas.dogcat.domain.adopt.converter.AdoptConverter;
import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.adopt.repository.AdoptRepository;
import kpaas.dogcat.domain.donate.donation.service.DonationQueryService;
import kpaas.dogcat.domain.member.service.MemberQueryService;
import kpaas.dogcat.domain.pet.converter.PetConverter;
import kpaas.dogcat.domain.pet.dto.PetResDto;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.enums.Breed;
import kpaas.dogcat.domain.stories.dailyStory.service.DailyStoryQueryService;
import kpaas.dogcat.domain.stories.review.service.ReviewQueryService;
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
    private final AdoptRepository adoptRepository;
    private final AdoptConverter adoptConverter;
    private final MemberQueryService memberQueryService;
    private final PetConverter petConverter;

    public Adopt findById(Long adoptId) {
        return adoptRepository.findById(adoptId).orElseThrow(() -> new CustomException(ErrorCode.ADOPTION_NOTFOUND));
    }

    public PetResDto.PetChatDto getAdoptionForChatting(Adopt adopt) {
        log.info("[ 채팅방 상단 입양 공고 조회 ]");
        Pet pet = adopt.getPet();
        return petConverter.toPetChatDto(pet);
    }

    public AdoptResDto.HomeDto getHomeData() {
        log.info("[ 홈 - 메인 화면 조회 ]");
        return AdoptResDto.HomeDto.builder()
                .popularReviews(reviewQueryService.get3PopularReviews())
                .popularStories(dailyStoryQueryService.get3PopularStories())
                .closingSoonDonations(donationQueryService.get3ClosingSoonDonations())
                .latestAdoptions(get3LatestAdoptions())
                .build();
    }

    public List<AdoptResDto.AdoptPreviewDto> get3LatestAdoptions() {
        Pageable pageable = PageRequest.of(0, 3);

        List<Adopt> adopts = adoptRepository.findTop3ByStatusOrderByDeadlineAsc(AdoptionStatus.ACTIVE, pageable);
        return adopts.stream()
                .map(adoption -> {
                    Pet pet = adoption.getPet();
                    String thumbnailUrl = adoption.getImages().split(",")[0];
                    String dDay = getDday(adoption);
                    return adoptConverter.toPreviewDto(dDay, thumbnailUrl, pet, adoption);
                })
                .toList();
    }

    /** 입양 공고 상태 + 품종 + 지역 + 시군구 별 조회**/
    public AdoptResDto.AdoptPreviewListDto getAdoptions(Long cursor, int size,
                                                        AdoptionStatus status,
                                                        Breed breed,
                                                        Region region,
                                                        String district,
                                                        String keyword) {

        log.info("[ 입양 공고 메인 화면 조회 ]");
        List<Adopt> adoptions = adoptRepository.searchAdoptions
                (cursor, size, status, breed, region, district, keyword);

        List<AdoptResDto.AdoptPreviewDto> adoptionDtos = adoptions.stream()
                .map(adoption -> {
                    Pet pet = adoption.getPet();
                    String thumbnailUrl = adoption.getImages().split(",")[0];
                    String dDay = getDday(adoption);
                    return adoptConverter.toPreviewDto(dDay, thumbnailUrl, pet, adoption);
                })
                .toList();

        Long nextCursor = adoptions.size() < size ? null : adoptions.get(adoptions.size() - 1).getId();

        return adoptConverter.toPreviewListDto(adoptionDtos, nextCursor);
    }

    /** 입양 공고 상세 페이지 조회 */
    public AdoptResDto.AdoptDetailDto getDetails(Long adoptId){
        log.info("[ 입양 공고 상세 페이지 조회: {} ]", adoptId);
        Adopt adopt = adoptRepository.findWithPetById(adoptId)
                .orElseThrow(() -> new CustomException(ErrorCode.ADOPTION_NOTFOUND));

        return adoptConverter.toAdoptDetailDto(adopt);
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

    /** 마이페이지 - 입양 현황 조회 */
    public AdoptResDto.MyAdoptionListDto getMyAdoptionList(String memberId, Long cursor, int size){
        log.info("[ 마이페이지 - 입양 현황 조회 ]");
        memberQueryService.findById(memberId);
        Pageable pageable = PageRequest.of(0, size);

        List<Adopt> adoptList = (cursor == null)
                ? adoptRepository.findByAdopterIdOrderByIdDesc(memberId, pageable)
                : adoptRepository.findByAdopterIdAndIdLessThanOrderByIdDesc(memberId, cursor, pageable);

        List<AdoptResDto.MyAdoptionDto> adoptionDtos = adoptList.stream()
                .map(adoptConverter::toMyAdoptionDto)
                .toList();

        Long nextCursor = adoptList.isEmpty() ? null : adoptList.get(adoptList.size() - 1).getId();
        return adoptConverter.toMyAdoptionListDto(adoptionDtos, nextCursor);
    }
}