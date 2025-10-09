package kpaas.dogcat.global.vc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "VC 관련 API")
@RestController
@RequestMapping("/api/vc")
@RequiredArgsConstructor
public class VcController {

    private final VcService vcService;

    @Operation(summary = "VCJWT", description = "인증된 사용자의 vcJwt를 받는 API 입니다.")
    @PostMapping("/sync")
    public CustomResponse<List<VcReqDTO.PetVcDTO>> sync(@RequestBody VcReqDTO.PetVcSyncReqDTO req) {
        List<VcReqDTO.PetVcDTO> petDtos = vcService.parseMultipleVcs(req);
        return CustomResponse.onSuccess(SuccessCode.OK, petDtos);
    }
}
