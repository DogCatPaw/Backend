package kpaas.dogcat.global.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kpaas.dogcat.global.payment.entity.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
    boolean existsByPaymentKey(String paymentKey);
}
