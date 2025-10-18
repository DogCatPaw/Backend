package kpaas.dogcat.domain.donate.donation.converter;

import kpaas.dogcat.domain.donate.donation.dto.DonationReqDto;
import kpaas.dogcat.domain.donate.donation.dto.DonationResDto;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donationList.dto.DonationListResDto;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.entity.Pet;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class DonationConverter {

    public Donation toDonation(Member member, Pet pet, DonationReqDto.CreateDto dto,
                               String accountNumber) {
        return Donation.builder()
                .member(member)
                .pet(pet)
                .title(dto.getTitle())
                .targetAmount(dto.getTargetAmount())
                .deadline(dto.getDeadline())
                .category(dto.getCategory())
                .content(dto.getContent())
                .images(dto.getImages())
                .bankName(dto.getBankName())
                .accountNumber(accountNumber)
                .accountHolder(dto.getAccountHolder())
                .build();
    }

    public DonationResDto.CreateDto toCreateDto(Donation savedDonation) {
        return DonationResDto.CreateDto.builder()
                .memberId(savedDonation.getMember().getId())
                .donationId(savedDonation.getId())
                .petDid(savedDonation.getPet().getDid())
                .images(savedDonation.getImages())
                .build();
    }

    public DonationResDto.DetailDto toDetailDto(String dDay, int patronCount, int progress,
                                                Donation donation,
                                                DonationListResDto.DonationListDto donationListDto) {
        return DonationResDto.DetailDto.builder()
                .dDay(dDay)
                .memberId(donation.getMember().getId())
                .petName(donation.getPet().getPetName())
                .petDid(donation.getPet().getDid())
                .breed(donation.getPet().getBreed())
                .title(donation.getTitle())
                .content(donation.getContent())
                .donationStatus(donation.getStatus())
                .targetAmount(donation.getTargetAmount())
                .currentAmount(donation.getCurrentAmount())
                .deadline(donation.getDeadline())
                .category(donation.getCategory())
                .images(donation.getImages())
                .patronCount(patronCount)
                .progress(progress)
                .recentDonations(donationListDto.getDonations())
                .cursor(donationListDto.getCursor())
                .build();
    }

    public DonationResDto.PreviewDto toHomeDto(String thumbnailUrl, String dDay,
                                               int patronCount, int progress, Donation donation) {
        return DonationResDto.PreviewDto.builder()
                .donationId(donation.getId())
                .dDay(dDay)
                .thumbnail(thumbnailUrl)
                .title(donation.getTitle())
                .currentAmount(donation.getCurrentAmount())
                .targetAmount(donation.getTargetAmount())
                .donationStatus(donation.getStatus())
                .patronCount(patronCount)
                .progress(progress)
                .build();
    }
}
