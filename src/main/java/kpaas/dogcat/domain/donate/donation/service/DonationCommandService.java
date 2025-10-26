package kpaas.dogcat.domain.donate.donation.service;

import jakarta.transaction.Transactional;
import kpaas.dogcat.domain.donate.donation.converter.DonationConverter;
import kpaas.dogcat.domain.donate.donation.dto.DonationReqDto;
import kpaas.dogcat.domain.donate.donation.dto.DonationResDto;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donation.enums.DonationStatus;
import kpaas.dogcat.domain.donate.donation.repository.DonationRepository;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.service.PetQueryService;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import kpaas.dogcat.global.objectStorage.ObjectStorageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DonationCommandService {

    private final DonationRepository donationRepository;
    private final AuthCommandService authCommandService;
    private final PetQueryService petQueryService;
    private final DonationConverter donationConverter;
    private final ObjectStorageUtil objectStorageUtil;
    private static final List<DonationStatus> BLOCKING_STATUSES = List.of(DonationStatus.ACTIVE, DonationStatus.ACHIEVED);

    public DonationResDto.CreateDto createDonation(DonationReqDto.CreateDto dto){

        // 회원과 펫 조회
        Member member = authCommandService.findById(dto.getMemberId());
        Pet pet = petQueryService.findById(dto.getPetId());

        // 진행 중인 공고가 하나라도 있으면 생성 차단
        if (donationRepository.existsByPetIdAndMemberIdAndStatusIn(
                dto.getPetId(), dto.getMemberId(), BLOCKING_STATUSES)) {
            throw new CustomException(ErrorCode.ALREADY_ACTIVE_DONATION);
        }

//        List<String> imageUrls = objectStorageUtil.uploadMultiple(images);
//        String joinedUrls = String.join(",", imageUrls);

        // 후원 공고 저장
        String accountNumber = dto.getAccountNumber().replace("-", "");
        Donation donation = donationConverter.toDonation(member, pet, dto, accountNumber);
        Donation savedDonation = donationRepository.save(donation);

        return donationConverter.toCreateDto(savedDonation);
    }

    // 마감일 지난 후원 공고 -> CLOSED
    public void closeDonation() {
        List<Donation> openDonations = donationRepository
                .findByStatusIn(List.of(DonationStatus.ACTIVE, DonationStatus.ACHIEVED));
        LocalDate today = LocalDate.now();

        // 마감일이 지난 공고 상태를 CLOSED로 변경
        for (Donation donation : openDonations) {
            if (today.isAfter(donation.getDeadline())) {
                donation.changeStatus(DonationStatus.CLOSED);
                log.info("[ 후원 공고 마감 처리 완료: '{}' (ID={}) ]", donation.getTitle(), donation.getId());
            }
        }
        donationRepository.saveAll(openDonations);
        log.info("[ 후원 공고 마감 처리 완료 ]");
    }

    // 매월 20일 정산 -> 정산 완료
    public void settleDonation(){
        List<Donation> closedDonations = donationRepository.findByStatus(DonationStatus.CLOSED);

        for (Donation donation : closedDonations) {
            // 수수료를 제외한 정산 금액 계산
            int current = donation.getCurrentAmount();
            int fee = (int) Math.floor(current * 0.03);
            int finalAmount = current - fee;

            donation.getMember().settleBone(finalAmount);
            donation.setPayoutAmount(finalAmount);
            donation.changeStatus(DonationStatus.SETTLED);
            log.info("[ 정산 금액 지급 - 후원 공고: {}번, 총액: {}, 지급금: {} ]", donation.getId(), current, finalAmount);
        }
        donationRepository.saveAll(closedDonations);
        log.info("[ 정산 금액 지급 완료 ]");
    }
}
