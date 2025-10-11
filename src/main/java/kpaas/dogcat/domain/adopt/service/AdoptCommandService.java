package kpaas.dogcat.domain.adopt.service;

import kpaas.dogcat.domain.adopt.converter.AdoptConverter;
import kpaas.dogcat.domain.adopt.repository.AdoptRepository;
import kpaas.dogcat.domain.adopt.dto.AdoptReqDto;
import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.service.PetQueryService;
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
        Adopt adopt = adoptConverter.toAdopt(pet, dto);
        adoptRepository.save(adopt);

        return adoptConverter.toRegisterDto(pet);
    }
}
