package kpaas.dogcat.domain.adopt.service;

import kpaas.dogcat.domain.adopt.converter.AdoptConverter;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.repository.AdoptRepository;
import kpaas.dogcat.domain.adopt.dto.AdoptReqDto;
import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.service.PetQueryService;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdoptCommandService {

    private final AuthCommandService authCommandService;
    private final PetQueryService petQueryService;
    private final AdoptRepository adoptRepository;
    private final AdoptConverter adoptConverter;
    private final AdoptQueryService adoptQueryService;

    /** 입양 공고 작성 **/
    public AdoptResDto.RegisterDto register(AdoptReqDto.RegisterDto dto, Long writerId) {
        Pet pet = petQueryService.findById(dto.getPetId());
        Member member = authCommandService.findById(writerId);
        if (adoptRepository.existsByPetId(dto.getPetId())) {
            throw new CustomException(ErrorCode.ALEADY_ACTIVE_ADOPTION);
        }
        Adopt adopt = adoptConverter.toAdopt(pet, member, dto);
        pet.setAdopt(adopt);            // 연관관계 양쪽 설정
        adoptRepository.save(adopt);    // 주인인 Pet만 save해도 adopt까지 cascade로 저장됨

        return adoptConverter.toRegisterDto(pet);
    }

    /** 입양 확인하기 -> 펫 소유권 이전 */
    public AdoptResDto.DelegateDto delegate(Long adoptId, Long adopterId) {
        Adopt adopt = adoptQueryService.findById(adoptId);

        if (adopt.getStatus() == AdoptionStatus.ADOPTING) {
            throw new CustomException(ErrorCode.ADOPTION_ADOPTING);
        } else if (adopt.getStatus() == AdoptionStatus.ADOPTED) {
            throw new CustomException(ErrorCode.ADOPTION_COMPLETED);
        }
        Member adopter = authCommandService.findById(adopterId);
        if (adopter.equals(adopt.getWriter())) {
            throw new CustomException(ErrorCode.ADOPTION_BAD_REQUEST);
        }
        Pet pet = adopt.getPet();

        // 소유권 이전 및 공고 완료 처리
        pet.changeOwner(adopter);
        adopt.updateStatus(AdoptionStatus.ADOPTED);

        return adoptConverter.toDelegateDto(adopt, adopterId);
    }
}
