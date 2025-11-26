package kpaas.dogcat.domain.shelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.shelter.dto.ShelterResDto;
import kpaas.dogcat.domain.shelter.service.ShelterQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "보호소 관련 API")
@RequestMapping("/api/shelter")
public class ShelterController {

    private final ShelterQueryService shelterQueryService;

    @Operation(summary = "메인 화면 - 보호소 조회", description = "보호소를 조회하는 API 입니다. 광역시/도, 군구, 보호소명 검색 가능")
    @GetMapping()
    public CustomResponse<ShelterResDto.ShelterPreviewListDto> getShelters(@RequestParam(required = false) Long cursor,
                                                                           @RequestParam(defaultValue = "8") int size,
                                                                           @RequestParam(required = false) Region region,
                                                                           @RequestParam(required = false) String district,
                                                                           @RequestParam(required = false) String keyword) {
        return CustomResponse.onSuccess(SuccessCode.OK,
                shelterQueryService.getShelters(cursor, size, region, district, keyword));
    }
}

