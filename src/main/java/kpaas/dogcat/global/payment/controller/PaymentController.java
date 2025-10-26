package kpaas.dogcat.global.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.auth.CurrentWalletAddress;
import kpaas.dogcat.global.payment.dto.PaymentReqDTO;
import kpaas.dogcat.global.payment.dto.PaymentResDTO;
import kpaas.dogcat.global.payment.service.PaymentCommandServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "후원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentCommandServiceImpl paymentCommandService;

    @Operation(summary = "뼈다귀 결제 준비", description = "뼈다귀를 구매하기 위해 orderId, amount를 미리 등록하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_404", description = "회원이 없습니다."),
            @ApiResponse(responseCode = "ITEM404", description = "구매할 물품이 없습니다.")
    })
    @PostMapping("/prepare")
    public CustomResponse<PaymentResDTO.PrepareDTO> request(@CurrentWalletAddress String walletAddress,
                                                            @RequestBody PaymentReqDTO.PrepareDTO dto) {
        return CustomResponse.onSuccess(SuccessCode.OK, paymentCommandService.preparePayment(dto, walletAddress));
    }

    @Operation(summary = "뼈다귀 토스 결제", description = "뼈다귀 구매를 위해 토스로 요청하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON201", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_404", description = "회원이 없습니다."),
            @ApiResponse(responseCode = "PAYMENT4041", description = "결제가 없습니다."),
            @ApiResponse(responseCode = "PAYMENT4002", description = "결제 금액이 일치하지 않습니다."),
            @ApiResponse(responseCode = "PAYMENT4003", description = "결제 인증은 완료되었으나, 아직 최종 승인되지 않았습니다."),
            @ApiResponse(responseCode = "PAYMENT4004", description = "결제가 취소되었습니다."),
            @ApiResponse(responseCode = "PAYMENT4005", description = "결제 승인이 실패했습니다."),
            @ApiResponse(responseCode = "PAYMENT4006", description = "결제 유효 시간이 만료되어 결제가 취소되었습니다."),
            @ApiResponse(responseCode = "PAYMENT4007", description = "결제가 부분 취소되었습니다."),
            @ApiResponse(responseCode = "PAYMENT5001", description = "결제/환불 상태가 불분명하여 처리에 실패했습니다."),
            @ApiResponse(responseCode = "PAYMENT5002", description = "토스 결제 응답이 없습니다.")
    })
    @PostMapping("/approve")
    public CustomResponse<PaymentResDTO.ApproveDTO> confirm(@CurrentWalletAddress String walletAddress,
                                                            @RequestBody PaymentReqDTO.ApproveDTO dto) {
        return CustomResponse.onSuccess(SuccessCode.OK, paymentCommandService.approvePayment(dto, walletAddress));
    }
}
