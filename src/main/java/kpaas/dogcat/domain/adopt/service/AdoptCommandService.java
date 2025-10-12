package kpaas.dogcat.domain.adopt.service;

import kpaas.dogcat.domain.adopt.converter.AdoptConverter;
import kpaas.dogcat.domain.adopt.repository.AdoptRepository;
import kpaas.dogcat.domain.adopt.dto.AdoptReqDto;
import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.domain.adopt.entity.Adopt;
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

    private final PetQueryService petQueryService;
    private final AdoptRepository adoptRepository;
    private final AdoptConverter adoptConverter;

    /** 입양 공고 작성 **/
    public AdoptResDto.RegisterDto register(AdoptReqDto.RegisterDto dto) {
        Pet pet = petQueryService.findById(dto.getPetId());
        if (adoptRepository.existsByPetId(dto.getPetId())) {
            throw new CustomException(ErrorCode.ALEADY_ACTIVE_ADOPTION);
        }
        Adopt adopt = adoptConverter.toAdopt(pet, dto);
        pet.setAdopt(adopt);            // 연관관계 양쪽 설정
        adoptRepository.save(adopt);    // 주인인 Pet만 save해도 adopt까지 cascade로 저장됨

        return adoptConverter.toRegisterDto(pet);
    }
}
