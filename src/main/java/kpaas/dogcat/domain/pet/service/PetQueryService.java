package kpaas.dogcat.domain.pet.service;

import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.domain.pet.converter.PetConverter;
import kpaas.dogcat.domain.pet.dto.PetResDto;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetQueryService {

    private final PetRepository petRepository;
    private final AuthCommandService authCommandService;
    private final PetConverter petConverter;

    public Pet findById(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOTFOUND));
    }

    public List<Pet> getMyPets(String memberId) {
        return petRepository.findAllByMemberId(memberId);
    }

    // 내 펫 목록 조회
    public List<PetResDto.MyPetDto> getMyPetList(String memberId) {
        log.info("[ 내 펫 목록 조회하기 - 사용자: {} ]", memberId);
        authCommandService.findById(memberId);
        List<Pet> pets = petRepository.findAllByMemberId(memberId);
        List<PetResDto.MyPetDto> myPetList = pets.stream()
                .map(petConverter::toMyPetDto)
                .toList();
        return myPetList;
    }
}
