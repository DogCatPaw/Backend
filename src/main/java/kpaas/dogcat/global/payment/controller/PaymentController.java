package kpaas.dogcat.global.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.jwt.CustomUserDetails;
import kpaas.dogcat.global.payment.dto.PaymentReqDTO;
import kpaas.dogcat.global.payment.dto.PaymentResDTO;
import kpaas.dogcat.global.payment.service.PaymentCommandServiceImpl;

@Tag(name = "후원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentCommandServiceImpl paymentCommandService;

    @Operation(summary = "뼈다귀 결제 준비", description = "뼈다귀를 구매하기 위해 orderId, amount를 미리 등록하는 API 입니다.")
    @PostMapping("/prepare")
    public CustomResponse<PaymentResDTO.PrepareDTO> request(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @RequestBody PaymentReqDTO.PrepareDTO dto) {
        return CustomResponse.onSuccess(SuccessCode.OK, paymentCommandService.preparePayment(dto, userDetails.getId()));
    }

    @Operation(summary = "뼈다귀 토스 결제", description = "뼈다귀 구매를 위해 토스로 요청하는 API 입니다.")
    @PostMapping("/approve")
    public CustomResponse<PaymentResDTO.ApproveDTO> confirm(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @RequestBody PaymentReqDTO.ApproveDTO dto) {
        return CustomResponse.onSuccess(SuccessCode.OK, paymentCommandService.approvePayment(dto, userDetails.getId()));
    }
}
