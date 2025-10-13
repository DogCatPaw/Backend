package kpaas.dogcat.domain.donate.donation.service;

import kpaas.dogcat.domain.donate.donation.converter.DonationConverter;
import kpaas.dogcat.domain.donate.donation.dto.DonationResDto;
import kpaas.dogcat.domain.donate.donation.enums.DonationStatus;
import kpaas.dogcat.domain.donate.donation.repository.DonationRepository;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListResDto;
import kpaas.dogcat.domain.donate.donationList.service.DonationListQueryService;
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
public class DonationQueryService {

    private final DonationRepository donationRepository;
    private final DonationListQueryService donationListQueryService;
    private final DonationConverter donationConverter;

    public Donation findById(Long donationId) {
        return donationRepository.findById(donationId)
                .orElseThrow(() -> new CustomException(ErrorCode.DONATION_NOTFOUND));
    }

    // 후원 공고 상세 보기 + 후원 내역 조회
    public DonationResDto.DetailDto getDonationDetail(Long donationId, Long cursor, int size) {
        Donation donation = findById(donationId);

        String dDay = getDday(donation);
        int patronCount = getPatronCount(donation);
        int progress = getProgress(donation);

        //후원 내역 조회
        DonationListResDto.DonationListDto donationListDto
                = donationListQueryService.getDonationList(donationId, cursor, size);

        return donationConverter.toDetailDto(dDay, patronCount, progress, donation, donationListDto);
    }

    /** 후원 공고 마감일 임박순 3개 리턴 **/
    public List<DonationResDto.PreviewDto> get3ClosingSoonDonations() {
        Pageable pageable = PageRequest.of(0, 3);

        return donationRepository.findTop3ByStatusOrderByDeadlineAsc(DonationStatus.ACTIVE, pageable)
                .stream()
                .map(donation -> {
                    String dDay = getDday(donation);
                    int patronCount = getPatronCount(donation);
                    int progress = getProgress(donation);
                    return donationConverter.toHomeDto(dDay, patronCount, progress, donation);
                })
                .toList();
    }

    /** 후원 Status별 조회 기능 **/
    public DonationResDto.PreviewListDto getDonations(Long cursor, int size, DonationStatus status) {
        Pageable pageable = PageRequest.of(0, size);

        // 상태가 null이면 ACTIVE인 상태만 조회
        if (status == null || status == DonationStatus.SETTLED) {
            status = DonationStatus.ACTIVE;
        }

        // 특정 상태만 조회
        List<Donation> donations;
        if (cursor == null) {
            donations = donationRepository.findByStatusOrderByIdDesc(status, pageable);
        } else {
            donations = donationRepository.findByStatusAndIdLessThanOrderByIdDesc(status, cursor, pageable);
        }

        List<DonationResDto.PreviewDto> donationDtos = donations.stream()
                .map(donation -> {
                    String dDay = getDday(donation);
                    int patronCount = getPatronCount(donation);
                    int progress = getProgress(donation);
                    return donationConverter.toHomeDto(dDay, patronCount, progress, donation);
                })
                .toList();

        // 다음 커서 계산
        Long nextCursor = donations.size() < size ? null : donations.get(donations.size() - 1).getId();

        return DonationResDto.PreviewListDto.builder()
                .donations(donationDtos)
                .nextCursor(nextCursor)
                .build();
    }


    /** 디데이 계산 **/
    public String getDday(Donation donation){
        LocalDate today = LocalDate.now();
        long daysLeft = ChronoUnit.DAYS.between(today, donation.getDeadline());
        String dDay;

        if (daysLeft > 0) dDay = "D-" + daysLeft;
        else if (daysLeft == 0) dDay = "D-day";
        else dDay = "마감";

        return dDay;
    }

    /** 후원자 수 **/
    public int getPatronCount(Donation donation) {
        return donation.getDonationListList() != null
                ? donation.getDonationListList().size() : 0;
    }

    /** 후원율 계산 **/
    public int getProgress(Donation donation) {
        int progress = (donation.getTargetAmount() != 0) ?
                (int) Math.round((double) donation.getCurrentAmount() / donation.getTargetAmount() * 100) : 0;
        return progress;
    }
}
