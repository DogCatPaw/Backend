package kpaas.dogcat.domain.pet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.pet.dto.PetReqDTO;
import kpaas.dogcat.domain.pet.dto.PetResDto;
import kpaas.dogcat.domain.pet.service.PetCommandService;
import kpaas.dogcat.domain.pet.service.PetQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "반려동물 API")
@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetCommandService petCommandService;
    private final PetQueryService petQueryService;

    @Operation(summary = "반려동물 등록", description = "사용자별 반려동물 등록하는 API 입니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CustomResponse<PetResDto.registerPetResDto> register(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                @RequestPart PetReqDTO.registerPetReqDTO dto,
                                                                @RequestPart MultipartFile images) {
        return CustomResponse.onSuccess(SuccessCode.CREATED, petCommandService.register(userDetails.getId(), dto, images));
    }

    @Operation(summary = "내 반려동물 조회", description = "내 반려동물 조회하는 API 입니다. 마이페이지와 입양 공고 등록 시 사용하세요.")
    @GetMapping
    public CustomResponse<List<PetResDto.MyPetDto>> getMyPetList(@AuthenticationPrincipal CustomUserDetails userDetails){
        return CustomResponse.onSuccess(SuccessCode.OK, petQueryService.getMyPetList(userDetails.getId()));
    }
}
