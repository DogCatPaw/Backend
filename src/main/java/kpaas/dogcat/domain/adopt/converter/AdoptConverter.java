package kpaas.dogcat.domain.adopt.converter;

import kpaas.dogcat.domain.adopt.dto.AdoptReqDto;
import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.pet.entity.Pet;
import org.springframework.stereotype.Component;

@Component
public class AdoptConverter {

    public Adopt toAdopt(Pet pet, AdoptReqDto.RegisterDto dto) {
        return Adopt.builder()
                .pet(pet)
                .title(dto.getTitle())
                .region(dto.getRegion())
                .district(dto.getDistrict())
                .shelterName(dto.getShelterName())
                .contact(dto.getContact())
                .deadline(dto.getDeadLine())
                .build();
    }

    public AdoptResDto.RegisterDto toRegisterDto(Pet pet) {
        return AdoptResDto.RegisterDto.builder()
                .petId(pet.getId())
                .did(pet.getDid())
                .build();
    }
}
