package com.github.bruce_mig.payment_service.command.api.events;

import com.github.bruce_mig.commons.events.PaymentProcessedEvent;
import com.github.bruce_mig.commons.utils.PaymentStatus;
import com.github.bruce_mig.payment_service.command.api.data.Payment;
import com.github.bruce_mig.payment_service.command.api.data.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;

@Component
public class PaymentEventsHandler {

    private final PaymentRepository paymentRepository;

    public PaymentEventsHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event){
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentStatus(PaymentStatus.PAYMENT_COMPLETED)
                .time(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
    }
}
