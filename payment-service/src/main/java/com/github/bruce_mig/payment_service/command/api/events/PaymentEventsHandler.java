package com.github.bruce_mig.payment_service.command.api.events;

import com.github.bruce_mig.commons.events.PaymentCancelledEvent;
import com.github.bruce_mig.commons.events.PaymentProcessedEvent;
import com.github.bruce_mig.commons.utils.PaymentStatus;
import com.github.bruce_mig.payment_service.command.api.data.Payment;
import com.github.bruce_mig.payment_service.command.api.data.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Component
@Slf4j
public class PaymentEventsHandler {

    private final PaymentRepository paymentRepository;

    public PaymentEventsHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event){
        String paymentId = event.getPaymentId();

        Payment payment = Payment.builder()
                .paymentId(paymentId)
                .orderId(event.getOrderId())
                .paymentStatus(PaymentStatus.PAYMENT_COMPLETED)
                .time(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
        log.info("Handled PaymentProcessed event: {}", event);
    }

    @EventHandler
    public void on(PaymentCancelledEvent event){
        String paymentId = event.getPaymentId();
        Payment payment = paymentRepository.findById(paymentId)
                        .orElseThrow(() -> new NoSuchElementException("Payment not found with id: " + paymentId));

        payment.setPaymentStatus(event.getPaymentStatus());
        paymentRepository.save(payment);
        log.info("Handled PaymentCancelled event: {}", event);
    }
}
