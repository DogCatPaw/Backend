package kpaas.dogcat.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kpaas.dogcat.domain.payment.entity.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
    boolean existsByPaymentKey(String paymentKey);
}
