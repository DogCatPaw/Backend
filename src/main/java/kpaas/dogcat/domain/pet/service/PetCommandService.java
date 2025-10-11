package kpaas.dogcat.domain.pet.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.pet.converter.PetConverter;
import kpaas.dogcat.domain.pet.dto.PetReqDTO;
import kpaas.dogcat.domain.pet.dto.PetResDto;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
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

    public PetResDto.registerPetResDto register(Long memberId, PetReqDTO.registerPetReqDTO dto){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        Pet pet = petConverter.toPet(member, dto);
        Pet savedPet = petRepository.save(pet);
        log.info("[ Pet registered successfully ]");
        return new PetResDto.registerPetResDto(memberId, savedPet.getId(), savedPet.getDid(), savedPet.getPetName());
    }
}
