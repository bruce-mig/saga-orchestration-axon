package com.github.bruce_mig.commons.events;

import com.github.bruce_mig.commons.utils.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCancelledEvent {
    private String paymentId;
    private String orderId;
    private PaymentStatus paymentStatus;
}
