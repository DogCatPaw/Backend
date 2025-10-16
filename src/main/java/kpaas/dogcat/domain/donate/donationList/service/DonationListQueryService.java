package kpaas.dogcat.domain.donate.donationList.service;

import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donation.repository.DonationRepository;
import kpaas.dogcat.domain.donate.donationList.converter.DonationListConverter;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListResDto;
import kpaas.dogcat.domain.donate.donationList.entity.DonationList;
import kpaas.dogcat.domain.donate.donationList.repository.DonationListRepository;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DonationListQueryService {

    private final DonationListRepository donationListRepository;
    private final AuthCommandService authCommandService;
    private final DonationListConverter donationListConverter;
    private final DonationRepository donationRepository;

    public DonationListResDto.DonationDto getDonation(String memberId, Long donationId) {
        Member member = authCommandService.findById(memberId);
        DonationList donationList = findById(donationId);

        return donationListConverter.toDonationDto(member, donationList);
    }

    // 특정 후원에 대해 여러명의 후원자 목록을 보여주기
    public DonationListResDto.DonationListDto getDonationList(Long donationId, Long cursor, int size) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new CustomException(ErrorCode.DONATION_NOTFOUND));
        Pageable pageable = PageRequest.of(0, size);

        List<DonationList> donationLists;
        if (cursor == null) {
            donationLists = donationListRepository.findByDonationOrderByIdDesc(donation, pageable);
        } else {
            donationLists = donationListRepository.findByDonationAndIdLessThanOrderByIdDesc(donation, cursor, pageable);
        }

        List<DonationListResDto.DonationDto> donations = donationLists.stream()
                .map(donationList -> donationListConverter.toDonationDto(
                        donationList.getMember(), donationList))
                .toList();

        Long nextCursor = donationLists.isEmpty()
                ? null : donationLists.get(donationLists.size() - 1).getId();

        return donationListConverter.toDonationListDto(donations, nextCursor);
    }

    public DonationList findById(Long donationId) {
        return donationListRepository.findById(donationId)
                .orElseThrow(() -> new CustomException(ErrorCode.DONATIONLIST_NOTFOUND));
    }

    /** 내 후원 잔액 조회하기 */
    public DonationListResDto.MyBoneBalanceDto  getMyBoneBalance(String memberId) {
        Member member = authCommandService.findById(memberId);
        return new DonationListResDto.MyBoneBalanceDto(member.getBoneBalance());
    }

    /** 내 후원 내역 목록 조회하기 */
    public DonationListResDto.MyDonationListDto getMyDonationList(String memberId, Long cursor, int size) {
        Member member = authCommandService.findById(memberId);
        Pageable pageable = PageRequest.of(0, size);

        List<DonationList> donationLists = (cursor == null)
                ? donationListRepository.findByMemberIdOrderByIdDesc(memberId, pageable)
                : donationListRepository.findByMemberIdAndIdLessThanOrderByIdDesc(memberId, cursor, pageable);

        List<DonationListResDto.MyDonationDto> donationDtos = donationLists.stream()
                .map(donationListConverter::toMyDonationDto)
                .toList();

        Long nextCursor = donationLists.isEmpty() ? null : donationLists.get(donationLists.size() - 1).getId();

        // 총 후원 금액 및 잔액 뼈다귀
        Integer totalAmount = donationListRepository.getTotalDonationAmount(memberId);
        Integer currentBoneBalance = member.getBoneBalance();

        return donationListConverter.toMyDonationListDto(totalAmount, currentBoneBalance,
                donationDtos, nextCursor);
    }
}
