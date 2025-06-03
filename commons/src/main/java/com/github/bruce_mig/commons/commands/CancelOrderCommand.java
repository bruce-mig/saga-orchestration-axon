package com.github.bruce_mig.commons.commands;

import com.github.bruce_mig.commons.utils.OrderStatus;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private OrderStatus orderStatus = OrderStatus.ORDER_CANCELLED;
}
