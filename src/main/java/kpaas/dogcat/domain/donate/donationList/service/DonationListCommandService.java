package kpaas.dogcat.domain.donate.donationList.service;

import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donation.enums.DonationStatus;
import kpaas.dogcat.domain.donate.donation.service.DonationQueryService;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListReqDto;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListResDto;
import kpaas.dogcat.domain.donate.donationList.converter.DonationListConverter;
import kpaas.dogcat.domain.donate.donationList.entity.DonationList;
import kpaas.dogcat.domain.donate.donationList.repository.DonationListRepository;
import kpaas.dogcat.domain.donate.item.Item;
import kpaas.dogcat.domain.donate.item.ItemRepository;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationListCommandService {

    private final DonationQueryService donationQueryService;
    private final DonationListRepository donationListRepository;
    private final AuthCommandService authCommandService;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final DonationListConverter donationListConverter;

    @Transactional
    public DonationListResDto.DonateDto donate(DonationListReqDto.DonateDto dto){

        // 후원 공고 확인
        Donation donation = donationQueryService.findById(dto.getDonationId());
        Member member = authCommandService.findById(dto.getMemberId());
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOTFOUND));

        // 마감일 지난 경우 후원 불가
        LocalDate now = LocalDate.now();
        if (now.isAfter(donation.getDeadline())) {
            throw new CustomException(ErrorCode.DONATION_INVALID);
        }

        // 목표 달성하면 ACHIEVED, 후원 더이상 못하게 하려했는데, 마감일전까지 가능하도록했음.
//        int remaining = donation.getTargetAmount() - donation.getCurrentAmount();
//        if (item.getPrice() > remaining) {
//            throw new CustomException(ErrorCode.DONATION_OVERFLOW);
//        }

        // 뼈다귀 잔액 확인 및 차감
        Integer bones = member.getBoneBalance();
        log.info("[ 현재 잔액: {} ]", bones);
        if (bones <= 0){
            throw new CustomException(ErrorCode.BONE_NOTFOUND);
        }
        Integer boneBalance = member.decreaseBone(item.getPrice());
        memberRepository.save(member);
        log.info("[ 후원 후 잔액: {} ]", boneBalance);

        // 후원 저장
        DonationList donationList = donationListConverter.toDonationList(item, member, donation);
        donationListRepository.save(donationList);

        int newAmount = donation.getCurrentAmount() + item.getPrice();
        donation.updateCurrentAmount(newAmount);    //현재 누적금액

        if (newAmount >= donation.getTargetAmount() && donation.getStatus() == DonationStatus.ACTIVE) {
            donation.changeStatus(DonationStatus.ACHIEVED);
            log.info("[ 목표 금액 달성 - 후원ID={}, 목표금액={}, 현재금액={} ]",
                    donation.getId(), donation.getTargetAmount(), newAmount);
        }

        return donationListConverter.toDonateDTO(donationList, boneBalance);
    }
}
