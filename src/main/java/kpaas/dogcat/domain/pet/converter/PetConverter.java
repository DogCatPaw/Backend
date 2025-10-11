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
                .breed(dto.getBreed())
                .old(dto.getOld())
                .weight(dto.getWeight())
                .gender(dto.getGender())
                .color(dto.getColor())
                .isNeutral(dto.isNeutral())
                .specifics(dto.getSpecifics())
                .build();
    }

    public PetResDto.MyPetListDto toMyPetListDto(Pet pet) {
        return PetResDto.MyPetListDto.builder()
                .did(pet.getDid())
                .petProfile(pet.getPetProfile())
                .petName(pet.getPetName())
                .old(pet.getOld())
                .weight(pet.getWeight())
                .gender(pet.getGender())
                .breed(pet.getBreed())
                .color(pet.getColor())
                .isNeutral(pet.isNeutral())
                .specifics(pet.getSpecifics())
                .build();
    }
}
