package kpaas.dogcat.domain.pet.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.service.MemberQueryService;
import kpaas.dogcat.domain.pet.converter.PetConverter;
import kpaas.dogcat.domain.pet.dto.PetReqDTO;
import kpaas.dogcat.domain.pet.dto.PetResDto;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetCommandService {

    private final PetRepository petRepository;
    private final PetConverter petConverter;
    private final MemberQueryService memberQueryService;

    public PetResDto.RegisterPetResDto register(String memberId, PetReqDTO.registerPetReqDTO dto){

        Member member = memberQueryService.findById(memberId);
        Pet pet = petConverter.toPet(member, dto);
        Pet savedPet = petRepository.save(pet);
        log.info("[ Pet 등록 완료 ]");
        return new PetResDto.RegisterPetResDto(memberId, savedPet.getId(), savedPet.getDid(), savedPet.getPetName());
    }
}
