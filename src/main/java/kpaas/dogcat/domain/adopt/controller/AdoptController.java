package kpaas.dogcat.domain.adopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.adopt.service.AdoptCommandService;
import kpaas.dogcat.domain.adopt.service.AdoptQueryService;
import kpaas.dogcat.domain.adopt.dto.AdoptReqDto;
import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "입양 관련 API")
@RequestMapping("/api/adoption")
public class AdoptController {

    private final AdoptQueryService adoptQueryService;
    private final AdoptCommandService adoptCommandService;

    @Operation(summary = "홈 화면 조회", description = "입양 공고, 후원글, 일상 일지, 입양 후기 3개씩 조회하는 API 입니다.")
    @GetMapping("/home")
    public CustomResponse<AdoptResDto.HomeDto> home() {
        return CustomResponse.onSuccess(SuccessCode.OK, adoptQueryService.getHomeData());
    }

    @Operation(summary = "입양 공고 작성", description = "입양 공고를 작성하는 API 입니다. 펫 등록이 먼저 필요합니다.")
    @PostMapping("/")
    public CustomResponse<AdoptResDto.RegisterDto> register(@RequestBody AdoptReqDto.RegisterDto dto) {
        return CustomResponse.onSuccess(SuccessCode.OK, adoptCommandService.register(dto));
    }
}
