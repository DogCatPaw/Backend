package kpaas.dogcat.domain.pet.service;

import kpaas.dogcat.domain.adopt.service.AdoptQueryService;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.domain.pet.converter.PetConverter;
import kpaas.dogcat.domain.pet.dto.PetReqDTO;
import kpaas.dogcat.domain.pet.dto.PetResDto;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import kpaas.dogcat.global.objectStorage.ObjectStorageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetCommandService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;
    private final PetConverter petConverter;
    private final ObjectStorageUtil objectStorageUtil;

    public PetResDto.registerPetResDto register(Long memberId, PetReqDTO.registerPetReqDTO dto, MultipartFile images){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        String uploaded = objectStorageUtil.upload(images);
        Pet pet = petConverter.toPet(member, dto, uploaded);
        Pet savedPet = petRepository.save(pet);
        log.info("[ Pet registered successfully ]");
        return new PetResDto.registerPetResDto(memberId, savedPet.getId(), savedPet.getDid(), savedPet.getPetName());
    }
}
