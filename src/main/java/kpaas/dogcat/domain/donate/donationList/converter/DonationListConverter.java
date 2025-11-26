package kpaas.dogcat.domain.donate.donationList.converter;

import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListResDto;
import kpaas.dogcat.domain.donate.donationList.entity.DonationList;
import kpaas.dogcat.domain.donate.item.Item;
import kpaas.dogcat.domain.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DonationListConverter {
    public DonationList toDonationList(Item item, Member member, Donation donation) {
        return DonationList.builder()
                .amount(item.getPrice())
                .member(member)
                .donation(donation)
                .build();
    }

    public DonationListResDto.DonateDto toDonateDTO(DonationList donationList, Integer bones) {
         return DonationListResDto.DonateDto.builder()
                .donationId(donationList.getId())
                .memberId(donationList.getMember().getId())
                .donationAmount(donationList.getAmount())   // 후원금액
                .boneBalance(bones)                         // 남은금액
                .build();
    }

    public DonationListResDto.DonationDto toDonationDto(Member member, DonationList donationList) {
        return DonationListResDto.DonationDto.builder()
                .profileUrl(member.getProfileUrl())
                .nickname(member.getNickname())
                .donationAmount(donationList.getAmount())
                .donationTime(donationList.getCreatedAt())
                .build();
    }

    public DonationListResDto.DonationListDto toDonationListDto(
            List<DonationListResDto.DonationDto> donations, Long nextCursor) {
        return DonationListResDto.DonationListDto.builder()
                .donations(donations)
                .cursor(nextCursor)
                .build();
    }

    public DonationListResDto.MyDonationDto toMyDonationDto(DonationList donationList) {
        Donation donation = donationList.getDonation();

        return DonationListResDto.MyDonationDto.builder()
                .donationTitle(donation.getTitle())       // 공고 제목
                .donationAmount(donationList.getAmount()) // 내가 후원한 금액
                .donationTime(donationList.getCreatedAt())// 후원한 시간
                .build();
    }

    public DonationListResDto.MyDonationListDto toMyDonationListDto(Integer totalAmount, Integer currentBoneBalance,
                                                                    List<DonationListResDto.MyDonationDto> donationDtos,
                                                                    Long nextCursor) {
        return DonationListResDto.MyDonationListDto.builder()
                .totalAmount(totalAmount)
                .currentBoneBalance(currentBoneBalance)
                .donations(donationDtos)
                .cursor(nextCursor)
                .build();
    }
}
