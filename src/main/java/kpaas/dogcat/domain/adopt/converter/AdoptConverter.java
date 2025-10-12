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
                .status(dto.getStatus())
                .build();
    }

    public AdoptResDto.RegisterDto toRegisterDto(Pet pet) {
        return AdoptResDto.RegisterDto.builder()
                .petId(pet.getId())
                .did(pet.getDid())
                .build();
    }

    public AdoptResDto.PreviewDto toPreviewDto(String dDay, Pet pet, Adopt adoption) {
        return AdoptResDto.PreviewDto.builder()
                .thumbnail(pet.getPetProfile())     //등록했던 펫 프로필 사용할지, 공고 사진 따로 올릴지 고민
                .title(adoption.getTitle())
                .breed(pet.getBreed())
                .did(pet.getDid())
                .region(adoption.getRegion())
                .district(adoption.getDistrict())
                .shelterName(adoption.getShelterName())
                .dDay(dDay)
                .build();
    }
}
