package com.github.bruce_mig.commons.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendNotificationCommand {
    @TargetAggregateIdentifier
    private String notificationId;
    private String orderId;
    private String userId;
}
