package kpaas.dogcat.domain.pet.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.domain.pet.converter.PetConverter;
import kpaas.dogcat.domain.pet.dto.PetReqDTO;
import kpaas.dogcat.domain.pet.dto.PetResDto;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import kpaas.dogcat.global.objectStorage.ObjectStorageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetCommandService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;
    private final PetConverter petConverter;
    private final ObjectStorageUtil objectStorageUtil;
    private final AuthCommandService authCommandService;

    public PetResDto.RegisterPetResDto register(String memberId, PetReqDTO.registerPetReqDTO dto){

        Member member = authCommandService.findById(memberId);

//        String uploaded = objectStorageUtil.upload(images);
        Pet pet = petConverter.toPet(member, dto);
        Pet savedPet = petRepository.save(pet);
        log.info("[ Pet registered successfully ]");
        return new PetResDto.RegisterPetResDto(memberId, savedPet.getId(), savedPet.getDid(), savedPet.getPetName());
    }
}
