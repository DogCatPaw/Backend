package kpaas.dogcat.domain.adopt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.adopt.dto.AdoptResDto;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "입양 관련 API")
@RequestMapping("/api/adoption")
public class AdoptController {

    private final AdoptQueryService adoptQueryService;

    @Operation(summary = "홈 화면 조회", description = "입양 공고, 후원글, 일상 일지, 입양 후기 3개씩 조회하는 API 입니다.")
    @GetMapping()
    public CustomResponse<AdoptResDto.HomeDto> home() {
        return CustomResponse.onSuccess(SuccessCode.OK, adoptQueryService.getHomeData());
    }
}
