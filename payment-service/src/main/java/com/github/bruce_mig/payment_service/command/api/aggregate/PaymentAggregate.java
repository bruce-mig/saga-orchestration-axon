package com.github.bruce_mig.payment_service.command.api.aggregate;

import com.github.bruce_mig.commons.commands.CancelPaymentCommand;
import com.github.bruce_mig.commons.commands.ValidatePaymentCommand;
import com.github.bruce_mig.commons.events.PaymentCancelledEvent;
import com.github.bruce_mig.commons.events.PaymentProcessedEvent;
import com.github.bruce_mig.commons.utils.OrderStatus;
import com.github.bruce_mig.commons.utils.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private PaymentStatus paymentStatus;

    public PaymentAggregate() {
    }

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
        // Validate the Payment Details
        // Publish the PaymentProcessed Event
        log.info("Executing ValidatePaymentCommand for Order Id: {} and Payment Id: {}",
                validatePaymentCommand.getOrderId(),
                validatePaymentCommand.getPaymentId());

        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(
                validatePaymentCommand.getPaymentId(),
                validatePaymentCommand.getOrderId()
        );

        AggregateLifecycle.apply(paymentProcessedEvent);
        log.info("PaymentProcessedEvent applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event){
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
    }

    @CommandHandler
    public void handle(CancelPaymentCommand cancelPaymentCommand){
        PaymentCancelledEvent paymentCancelledEvent = new PaymentCancelledEvent();
        BeanUtils.copyProperties(
                cancelPaymentCommand,
                paymentCancelledEvent);
        AggregateLifecycle.apply(paymentCancelledEvent);
    }

    @EventSourcingHandler
    public void on(PaymentCancelledEvent event){
        this.paymentStatus = event.getPaymentStatus();
    }
}
