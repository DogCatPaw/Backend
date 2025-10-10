package kpaas.dogcat.domain.donate.donationList.converter;

import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donationList.entity.DonationList;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListResDto;
import kpaas.dogcat.domain.donate.item.Item;
import kpaas.dogcat.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class DonationListConverter {
    public DonationList toDonationList(Item item, Member member, Donation donation) {
        return DonationList.builder()
                .amount(item.getPrice())
                .member(member)
                .donation(donation)
                .build();
    }

    public DonationListResDto.Donate toDonateDTO(DonationList donationList, Integer bones) {
         return DonationListResDto.Donate.builder()
                .donationId(donationList.getId())
                .memberId(donationList.getMember().getId())
                .donationAmount(donationList.getAmount())   // 후원금액
                .boneBalance(bones)                         // 남은금액
                .build();
    }
}
