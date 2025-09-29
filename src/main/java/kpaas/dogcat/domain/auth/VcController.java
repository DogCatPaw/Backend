package kpaas.dogcat.domain.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "VC 관련 API")
@RequestMapping("/api/vc")
public class VcController {

    private final VcService vcService;

    @PostMapping
    public CustomResponse<VcResDTO.registerVcDTO> registerVC(@RequestBody VcReqDTO.registerVcDTO dto){
        return CustomResponse.onSuccess(SuccessCode.OK, vcService.registerVc(dto));
    }
}
