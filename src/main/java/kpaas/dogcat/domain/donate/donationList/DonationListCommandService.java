package kpaas.dogcat.domain.donate.donationList;

import kpaas.dogcat.domain.donate.donation.Donation;
import kpaas.dogcat.domain.donate.donation.DonationService;
import kpaas.dogcat.domain.donate.donationList.converter.DonationListConverter;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationListCommandService {

    private final DonationService donationService;
    private final DonationListRepository donationListRepository;
    private final AuthCommandService authCommandService;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final DonationListConverter donationListConverter;

    public DonationListResDTO.Donate donate(DonationListReqDTO.Donate dto){

        // 후원 공고 확인 및 결제한 뼈다귀가 있는지 확인
        Donation donation = donationService.findDonation(dto.getDonationId());
        Member member = authCommandService.findById(dto.getMemberId());
        Integer bones = member.getBoneBalance();
        log.info("[ 현재 잔액: {} ]", bones);
        if (bones <= 0){
            throw new CustomException(ErrorCode.BONE_NOTFOUND);
        }

        // 있으면 내 boneBalance를 업데이트
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOTFOUND));
        bones -= item.getPrice();
        member.decreaseBone(item.getPrice());
        memberRepository.save(member);
        log.info("[ 후원 후 잔액 ]: {}", bones);

        // 후원 저장
        DonationList donationList = donationListConverter.toDonationList(item, member, donation);
        donationListRepository.save(donationList);
        return donationListConverter.toDonateDTO(donationList, bones);
    }
}
