package kpaas.dogcat.domain.adopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.adopt.service.AdoptCommandService;
import kpaas.dogcat.domain.adopt.service.AdoptQueryService;
import kpaas.dogcat.domain.adopt.dto.AdoptReqDto;
import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PostMapping("/post")
    public CustomResponse<AdoptResDto.RegisterDto> register(@RequestBody AdoptReqDto.RegisterDto dto,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return CustomResponse.onSuccess(SuccessCode.OK, adoptCommandService.register(dto, userDetails.getId()));
    }

    @Operation(summary = "입양 공고 조회 (cursor 기반)", description = "입양 공고를 cursor로 조회하는 API 입니다. " +
            "\"cursor와 size에 아무 값도 입력하지 않아도 되며, 기본 사이즈는 9입니다. 다음 조회는 nextCursor을 사용하세요.")
    @GetMapping("/")
    public CustomResponse<AdoptResDto.PreviewListDto> getRegistration(@RequestParam(required = false) Long cursor,
                                                                      @RequestParam(defaultValue = "9") int size,
                                                                      @RequestParam(required = false) AdoptionStatus status,
                                                                      @RequestParam(required = false) Region region,
                                                                      @RequestParam(required = false) String district) {
        return CustomResponse.onSuccess(SuccessCode.OK, adoptQueryService.getAdoptions(
                cursor, size, status, region, district));
    }

    @Operation(summary = "입양 공고 상세 페이지 조회", description = "입양 공고의 상세 페이지를 조회하는 API 입니다.")
    @GetMapping("/detail")
    public CustomResponse<AdoptResDto.DetailDto> getDetail(@RequestParam Long adoptId) {
        return CustomResponse.onSuccess(SuccessCode.OK, adoptQueryService.getDetails(adoptId));
    }
}
