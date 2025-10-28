package kpaas.dogcat.domain.adopt.service;

import kpaas.dogcat.domain.adopt.converter.AdoptConverter;
import kpaas.dogcat.domain.adopt.dto.AdoptReqDto;
import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.repository.AdoptRepository;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.service.MemberQueryService;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.service.PetQueryService;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdoptCommandService {

    private final MemberQueryService memberQueryService;
    private final PetQueryService petQueryService;
    private final AdoptRepository adoptRepository;
    private final AdoptConverter adoptConverter;
    private final AdoptQueryService adoptQueryService;

    /** 입양 공고 작성 **/
    public AdoptResDto.RegisterDto register(AdoptReqDto.RegisterDto dto, String writerId) {
        Pet pet = petQueryService.findById(dto.getPetId());
        Member member = memberQueryService.findById(writerId);
        if (adoptRepository.existsByPetId(dto.getPetId())) {
            throw new CustomException(ErrorCode.ALEADY_ACTIVE_ADOPTION);
        }

        Adopt adopt = adoptConverter.toAdopt(pet, member, dto);
        pet.setAdopt(adopt);            // 연관관계 양쪽 설정
        adoptRepository.save(adopt);    // 주인인 Pet만 save해도 adopt까지 cascade로 저장됨

        return adoptConverter.toRegisterDto(pet);
    }

    /** 입양 확인하기 -> 펫 소유권 이전 */
    public AdoptResDto.DelegateDto delegate(Long adoptId, String adopterId) {
        Adopt adopt = adoptQueryService.findById(adoptId);

        if (adopt.getStatus() == AdoptionStatus.ADOPTING) {
            throw new CustomException(ErrorCode.ADOPTION_ADOPTING);
        } else if (adopt.getStatus() == AdoptionStatus.ADOPTED) {
            throw new CustomException(ErrorCode.ADOPTION_COMPLETED);
        } else if (adopt.getStatus() == AdoptionStatus.CLOSED) {
            throw new CustomException(ErrorCode.ADOPTION_CLOSED);
        }
        Member adopter = memberQueryService.findById(adopterId);
        if (adopter.equals(adopt.getWriter())) {
            throw new CustomException(ErrorCode.ADOPTION_BAD_REQUEST);
        }
        Pet pet = adopt.getPet();

        // 입양자 필드 저장 & 소유권 이전 & 공고 완료 처리
        adopt.apply(adopter);
        pet.changeOwner(adopter);
        adopt.updateStatus(AdoptionStatus.ADOPTED);
        log.info("[ 입양 완료 - 입양글: {}, 펫: {}, 상태: {} ]", adopt.getId(), pet.getId(), adopt.getStatus());

        return adoptConverter.toDelegateDto(adopt, adopterId);
    }

    public void patchAdoption(Long adoptionId, AdoptReqDto.RegisterDto dto, String walletAddress) {
        Adopt adoption = adoptQueryService.findById(adoptionId);
        Member member = memberQueryService.findById(walletAddress);

        if (!adoption.getWriter().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_401);
        }
        if (adoption.getStatus() != AdoptionStatus.ACTIVE) {
            throw new CustomException(ErrorCode.CANNOT_UPDATE_ADOPTION);
        }

        // dto의 petId가 내 펫 목록 안에 포함돼 있는지 확인
        List<Pet> myPets = petQueryService.getMyPets(walletAddress);
        boolean isMyPet = myPets.stream()
                .anyMatch(pet -> pet.getId().equals(dto.getPetId()));
        if (!isMyPet) {
            throw new CustomException(ErrorCode.PET_NOT_OWNED);
        }

        adoption.update(dto);
        log.info("[ 입양 공고 수정 완료 - 입양글: {}, 작성자: {}, 펫: {} ]",
                adoption.getId(), adoption.getWriter().getId(), adoption.getPet().getId());
    }

    public void closeAdoption() {
        List<Adopt> openAdoptions = adoptRepository.findByStatus(AdoptionStatus.ACTIVE);
        LocalDate today = LocalDate.now();

        // 마감일이 지난 공고 상태를 CLOSED로 변경
        for (Adopt adoption : openAdoptions) {
            if (today.isAfter(adoption.getDeadline())) {
                adoption.updateStatus(AdoptionStatus.CLOSED);
                log.info("[ 마감일 지난 입양 공고 마감 처리 완료 - ID: {} ]", adoption.getId());
            }
        }
        adoptRepository.saveAll(openAdoptions);
        log.info("[ 압양 공고 마감 처리 완료 ]");
    }
}
