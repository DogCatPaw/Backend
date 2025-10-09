package kpaas.dogcat.domain.payment.converter;

import org.springframework.stereotype.Component;
import kpaas.dogcat.domain.item.Item;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.payment.dto.PaymentResDTO;
import kpaas.dogcat.domain.payment.entity.Payment;
import kpaas.dogcat.domain.payment.enums.OrderStatus;

@Component
public class PaymentConverter {
    public Payment toPayment(String orderId, String orderName, Item item, Member member) {
        return Payment.builder()
                .orderId(orderId)
                .orderName(orderName)
                .totalAmount(item.getPrice())
                .status(OrderStatus.READY)
                .member(member)
                .build();
    }

    public PaymentResDTO.PrepareDTO toPrepareDTO(Payment payment) {
        return PaymentResDTO.PrepareDTO.builder()
                .orderId(payment.getOrderId())
                .orderName(payment.getOrderName())
                .totalAmount(payment.getTotalAmount())
                .status(payment.getStatus())
                .build();
    }

    public PaymentResDTO.ApproveDTO toApproveDTO(PaymentResDTO.TossResponseDTO responseDto, Payment payment) {
        return PaymentResDTO.ApproveDTO.builder()
                .orderId(responseDto.getOrderId())
                .orderName(responseDto.getOrderName())
                .totalAmount(responseDto.getTotalAmount())
                .status(payment.getStatus())
                .build();
    }
}
