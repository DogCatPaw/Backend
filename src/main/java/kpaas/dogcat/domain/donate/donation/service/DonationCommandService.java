package kpaas.dogcat.domain.donate.donation.service;

import kpaas.dogcat.domain.donate.donation.converter.DonationConverter;
import kpaas.dogcat.domain.donate.donation.repository.DonationRepository;
import kpaas.dogcat.domain.donate.donation.dto.DonationReqDto;
import kpaas.dogcat.domain.donate.donation.dto.DonationResDto;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.service.PetQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationCommandService {

    private final DonationRepository donationRepository;
    private final AuthCommandService authCommandService;
    private final PetQueryService petQueryService;
    private final DonationConverter donationConverter;

    public DonationResDto.CreateDto createDonation(DonationReqDto.CreateDto dto){

        // 회원과 펫 조회
        Member member = authCommandService.findById(dto.getMemberId());
        Pet pet = petQueryService.findById(dto.getPetId());

        // 후원 공고 저장
        Donation donation = donationConverter.toDonation(member, pet, dto);
        Donation savedDonation = donationRepository.save(donation);

        return donationConverter.toCreateDto(savedDonation);
    }
}
