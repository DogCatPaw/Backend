package kpaas.dogcat.domain.adopt.converter;

import kpaas.dogcat.domain.adopt.dto.AdoptReqDto;
import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.entity.Pet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdoptConverter {

    public Adopt toAdopt(Pet pet, Member writer, AdoptReqDto.RegisterDto dto) {
        return Adopt.builder()
                .pet(pet)
                .writer(writer)
                .title(dto.getTitle())
                .images(dto.getImages())
                .region(dto.getRegion())
                .district(dto.getDistrict())
                .shelterName(dto.getShelterName())
                .contact(dto.getContact())
                .deadline(dto.getDeadline())
                .status(dto.getStatus())
                .build();
    }

    public AdoptResDto.RegisterDto toRegisterDto(Pet pet) {
        return AdoptResDto.RegisterDto.builder()
                .adoptId(pet.getAdopt().getId())
                .targetId(pet.getAdopt().getWriter().getId())
                .adoptTitle(pet.getAdopt().getTitle())
                .petId(pet.getId())
                .images(pet.getAdopt().getImages())
                .build();
    }

    public AdoptResDto.AdoptPreviewDto toPreviewChatDto(String dDay, String thumbnail, Pet pet, Adopt adoption) {
        return AdoptResDto.AdoptPreviewDto.builder()
                .adoptId(adoption.getId())
                .thumbnail(thumbnail)
                .title(adoption.getTitle())
                .breed(pet.getBreed())
                .did(pet.getDid())
                .region(adoption.getRegion())
                .district(adoption.getDistrict())
                .shelterName(adoption.getShelterName())
                .status(adoption.getStatus())
                .dDay(dDay)
                .build();
    }

    public AdoptResDto.AdoptPreviewDto toPreviewDto(String dDay, String thumbnail, Pet pet, Adopt adoption) {
        return AdoptResDto.AdoptPreviewDto.builder()
                .adoptId(adoption.getId())
                .thumbnail(thumbnail)
                .title(adoption.getTitle())
                .breed(pet.getBreed())
                .did(pet.getDid())
                .region(adoption.getRegion())
                .district(adoption.getDistrict())
                .shelterName(adoption.getShelterName())
                .status(adoption.getStatus())
                .dDay(dDay)
                .build();
    }

    public AdoptResDto.AdoptPreviewListDto toPreviewListDto(List<AdoptResDto.AdoptPreviewDto> adoptionDtos, Long nextCursor) {
        return AdoptResDto.AdoptPreviewListDto.builder()
                .adoptions(adoptionDtos)
                .nextCursor(nextCursor)
                .build();
    }

    public AdoptResDto.AdoptDetailDto toAdoptDetailDto(Adopt adopt) {
        return AdoptResDto.AdoptDetailDto.builder()
                .writerWallet(adopt.getWriter().getId())
                .title(adopt.getTitle())
                .content(adopt.getContent())
                .images(adopt.getImages())
                .did(adopt.getPet().getDid())
                .petProfile(adopt.getPet().getPetProfile())
                .petName(adopt.getPet().getPetName())
                .old(adopt.getPet().getOld())
                .weight(adopt.getPet().getWeight())
                .color(adopt.getPet().getColor())
                .isNeutral(adopt.getPet().isNeutral())
                .specifics(adopt.getPet().getSpecifics())
                .gender(adopt.getPet().getGender())
                .breed(adopt.getPet().getBreed())
                .region(adopt.getRegion())
                .district(adopt.getDistrict())
                .shelterName(adopt.getShelterName())
                .status(adopt.getStatus())
                .deadline(adopt.getDeadline())
                .build();
    }

    public AdoptResDto.DelegateDto toDelegateDto(Adopt adopt, String adopterId) {
        return AdoptResDto.DelegateDto.builder()
                .adopterId(adopterId)
                .adoptWriterId(adopt.getWriter().getId())
                .status(adopt.getStatus())
                .appliedAt(adopt.getAppliedAt())
                .build();
    }

    public AdoptResDto.MyAdoptionDto toMyAdoptionDto(Adopt adopt) {
        return AdoptResDto.MyAdoptionDto.builder()
                .adoptId(adopt.getId())
                .petName(adopt.getPet().getPetName())
                .region(adopt.getRegion())
                .district(adopt.getDistrict())
                .shelterName(adopt.getShelterName())
                .status(adopt.getStatus())
                .appliedAt(adopt.getAppliedAt())
                .build();
    }

    public AdoptResDto.MyAdoptionListDto toMyAdoptionListDto(List<AdoptResDto.MyAdoptionDto> adoptionDtos, Long nextCursor) {
        return AdoptResDto.MyAdoptionListDto.builder()
                .adoptions(adoptionDtos)
                .cursor(nextCursor)
                .build();
    }
}
