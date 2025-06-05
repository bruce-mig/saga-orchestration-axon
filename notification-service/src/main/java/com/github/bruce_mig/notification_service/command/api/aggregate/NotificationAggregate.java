package com.github.bruce_mig.notification_service.command.api.aggregate;

import com.github.bruce_mig.commons.commands.SendNotificationCommand;
import com.github.bruce_mig.commons.events.NotificationSentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class NotificationAggregate {

    @AggregateIdentifier
    private String notificationId;
    private String orderId;
    private String userId;

    public NotificationAggregate() {
    }

    @CommandHandler
    public NotificationAggregate(SendNotificationCommand sendNotificationCommand) {
        NotificationSentEvent notificationSentEvent = NotificationSentEvent.builder()
                .notificationId(sendNotificationCommand.getNotificationId())
                .orderId(sendNotificationCommand.getOrderId())
                .userId(sendNotificationCommand.getUserId())
                .build();

        AggregateLifecycle.apply(notificationSentEvent);
    }

    @EventSourcingHandler
    public void on(NotificationSentEvent event){
        this.notificationId = event.getNotificationId();
        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
    }
}
