package com.github.bruce_mig.commons.commands;

import com.github.bruce_mig.commons.utils.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompleteOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;
    private OrderStatus orderStatus;
}
