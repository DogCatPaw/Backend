package kpaas.dogcat.domain.pet.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.pet.dto.PetReqDTO;
import kpaas.dogcat.domain.pet.dto.PetResDTO;
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

    public PetResDTO.registerPetResDTO register(Long memberId, PetReqDTO.registerPetReqDTO dto){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));
        Pet pet = Pet.builder()
                .did(dto.getDid())
                .member(member)
                .petName(dto.getPetName())
                .breed(dto.getBreed())
                .old(dto.getOld())
                .weight(dto.getWeight())
                .gender(dto.getGender())
                .color(dto.getColor())
                .feature(dto.getFeature())
                .health(dto.getHealth())
                .specifics(dto.getSpecifics())
                .build();
        Pet savedPet = petRepository.save(pet);
        log.info("[ Pet registered successfully ]");
        return new PetResDTO.registerPetResDTO(memberId, savedPet.getDid(), savedPet.getPetName());
    }
}
