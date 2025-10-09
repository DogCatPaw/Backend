package kpaas.dogcat.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.jwt.CustomUserDetails;
import kpaas.dogcat.domain.payment.dto.PaymentReqDTO;
import kpaas.dogcat.domain.payment.dto.PaymentResDTO;
import kpaas.dogcat.domain.payment.service.PaymentCommandServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentCommandServiceImpl paymentCommandService;

    @PostMapping("/prepare")
    public CustomResponse<PaymentResDTO.PrepareDTO> request(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @RequestBody PaymentReqDTO.PrepareDTO dto) {
        return CustomResponse.onSuccess(SuccessCode.OK, paymentCommandService.preparePayment(dto, userDetails.getId()));
    }

    @PostMapping("/approve")
    public CustomResponse<PaymentResDTO.ApproveDTO> confirm(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @RequestBody PaymentReqDTO.ApproveDTO dto) {
        return CustomResponse.onSuccess(SuccessCode.OK, paymentCommandService.approvePayment(dto, userDetails.getId()));
    }
}
