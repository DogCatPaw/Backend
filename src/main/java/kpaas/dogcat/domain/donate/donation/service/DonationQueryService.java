package kpaas.dogcat.domain.donate.donation.service;

import kpaas.dogcat.domain.donate.donation.converter.DonationConverter;
import kpaas.dogcat.domain.donate.donation.dto.DonationResDto;
import kpaas.dogcat.domain.donate.donation.repository.DonationRepository;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListResDto;
import kpaas.dogcat.domain.donate.donationList.entity.DonationList;
import kpaas.dogcat.domain.donate.donationList.repository.DonationListRepository;
import kpaas.dogcat.domain.donate.donationList.service.DonationListCommandService;
import kpaas.dogcat.domain.donate.donationList.service.DonationListQueryService;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DonationQueryService {

    private final DonationRepository donationRepository;
    private final DonationListQueryService donationListQueryService;
    private final DonationConverter donationConverter;
    private final DonationListRepository donationListRepository;

    public Donation findById(Long donationId) {
        return donationRepository.findById(donationId)
                .orElseThrow(() -> new CustomException(ErrorCode.DONATION_NOTFOUND));
    }

    public DonationResDto.DetailDto getDonationDetail(Long donationId, Long cursor, int size) {
        Donation donation = findById(donationId);

        //누적 금액 계산
        Integer currentAmount = getCurrentAmount(donationId);

        //후원 내역 조회
        DonationListResDto.DonationListDto donationListDto
                = donationListQueryService.getDonationList(donationId, cursor, 5);

        return donationConverter.toDetailDto(donation, currentAmount, donationListDto);
    }

    public Integer getCurrentAmount(Long donationId) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new CustomException(ErrorCode.DONATION_NOTFOUND));

        // 모인 후원금을 계산
        List<DonationList> donationLists = donationListRepository.findByDonationId(donationId);
        Integer currentAmount = donation.getCurrentAmount();
        if (currentAmount == null) currentAmount = 0;
        for (DonationList donationList : donationLists) {
            currentAmount += donationList.getAmount();  // 단순히 합산하는 것이기 때문에 쿼리 ok
        }
        return currentAmount;
    }
}
