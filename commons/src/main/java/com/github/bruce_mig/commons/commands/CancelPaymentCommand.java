package com.github.bruce_mig.commons.commands;

import com.github.bruce_mig.commons.utils.PaymentStatus;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelPaymentCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private PaymentStatus paymentStatus = PaymentStatus.PAYMENT_CANCELLED;
}
