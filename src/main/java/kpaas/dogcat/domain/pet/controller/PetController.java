package kpaas.dogcat.domain.pet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.pet.dto.PetReqDTO;
import kpaas.dogcat.domain.pet.dto.PetResDTO;
import kpaas.dogcat.domain.pet.service.PetCommandService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "반려동물 API")
@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetCommandService petCommandService;

    @Operation(summary = "반려동물 등록", description = "사용자별 반려동물 등록하는 API 입니다.")
    @PostMapping
    public CustomResponse<PetResDTO.registerPetResDTO> register(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                @RequestBody PetReqDTO.registerPetReqDTO dto) {
        return CustomResponse.onSuccess(SuccessCode.CREATED, petCommandService.register(userDetails.getId(), dto));
    }
}
