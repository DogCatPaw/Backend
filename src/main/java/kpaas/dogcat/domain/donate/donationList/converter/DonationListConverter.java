package kpaas.dogcat.domain.donate.donationList.converter;

import kpaas.dogcat.domain.donate.donation.Donation;
import kpaas.dogcat.domain.donate.donationList.DonationList;
import kpaas.dogcat.domain.donate.donationList.DonationListResDTO;
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

    public DonationListResDTO.Donate toDonateDTO(DonationList donationList, Integer bones) {
         return DonationListResDTO.Donate.builder()
                .donationId(donationList.getId())
                .memberId(donationList.getMember().getId())
                .donationAmount(donationList.getAmount())   // 후원금액
                .boneBalance(bones)                         // 남은금액
                .build();
    }
}
