package kpaas.dogcat.domain.shelter.converter;

import kpaas.dogcat.domain.shelter.dto.ShelterResDto;
import kpaas.dogcat.domain.shelter.entity.Shelter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShelterConverter {

    public ShelterResDto.ShelterPreviewDto toPreviewDtos(Shelter shelter) {
        return ShelterResDto.ShelterPreviewDto.builder()
                .shelterId(shelter.getId())
                .shelterName(shelter.getShelterName())
                .region(shelter.getRegion())
                .district(shelter.getDistrict())
                .address(shelter.getAddress())
                .contact(shelter.getContact())
                .build();
    }

    public ShelterResDto.ShelterPreviewListDto toPreviewListDto(
            List<ShelterResDto.ShelterPreviewDto> shelterPreviewDtos,
            Long nextCursor) {
        return ShelterResDto.ShelterPreviewListDto.builder()
                .shelters(shelterPreviewDtos)
                .nextCursor(nextCursor)
                .build();
    }
}
