package kpaas.dogcat.domain.shelter.service;

import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.shelter.converter.ShelterConverter;
import kpaas.dogcat.domain.shelter.dto.ShelterResDto;
import kpaas.dogcat.domain.shelter.entity.Shelter;
import kpaas.dogcat.domain.shelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShelterQueryService {

    private final ShelterRepository shelterRepository;
    private final ShelterConverter shelterConverter;

    public ShelterResDto.ShelterPreviewListDto getShelters(Long cursor, int size,
                                                           Region region,
                                                           String district,
                                                           String keyword) {
        log.info("[ 보호소 메인 화면 조회 시작 ]");
        List<Shelter> shelters = shelterRepository.searchShelters(cursor, size, region, district, keyword);
        List<ShelterResDto.ShelterPreviewDto> shelterPreviewDtos = shelters.stream()
                .map(shelterConverter::toPreviewDtos)
                .toList();
        Long nextCursor = shelters.size() < size ? null : shelters.get(shelters.size() - 1).getId();

        return shelterConverter.toPreviewListDto(shelterPreviewDtos, nextCursor);
    }
}
