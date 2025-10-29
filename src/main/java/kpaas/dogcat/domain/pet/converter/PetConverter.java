package kpaas.dogcat.domain.pet.converter;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.dto.PetReqDTO;
import kpaas.dogcat.domain.pet.dto.PetResDto;
import kpaas.dogcat.domain.pet.entity.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetConverter {
    public Pet toPet(Member member, PetReqDTO.registerPetReqDTO dto) {
        return Pet.builder()
                .did(dto.getDid())
                .member(member)
                .petName(dto.getPetName())
                .petProfile(dto.getImages())
                .breed(dto.getBreed())
                .old(dto.getOld())
                .weight(dto.getWeight())
                .gender(dto.getGender())
                .color(dto.getColor())
                .isNeutral(dto.isNeutral())
                .specifics(dto.getSpecifics())
                .build();
    }

    public PetResDto.MyPetDto toMyPetDto(Pet pet) {
        return PetResDto.MyPetDto.builder()
                .petId(pet.getId())
                .did(pet.getDid())
                .petProfile(pet.getPetProfile())
                .petName(pet.getPetName())
                .old(pet.getOld())
                .gender(pet.getGender())
                .breed(pet.getBreed())
                .build();
    }

    public PetResDto.PetChatDto toPetChatDto(Pet pet) {
        return PetResDto.PetChatDto.builder()
                .petId(pet.getId())
                .adoptId(pet.getAdopt().getId())
                .writerWallet(pet.getMember().getId())
                .did(pet.getDid())
                .petProfile(pet.getPetProfile())
                .petName(pet.getPetName())
                .old(pet.getOld())
                .status(pet.getAdopt().getStatus())
                .gender(pet.getGender())
                .breed(pet.getBreed())
                .build();
    }
}
