package com.github.bruce_mig.shipment_service.command.api.aggregate;

import com.github.bruce_mig.commons.commands.ShipOrderCommand;
import com.github.bruce_mig.commons.events.OrderShippedEvent;
import com.github.bruce_mig.commons.utils.ShipmentStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ShipmentAggregate {

    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private ShipmentStatus shipmentStatus;

    public ShipmentAggregate() {
    }

    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand shipOrderCommand) {
        // todo: Validate the command
        // Publish the OrderShippedEvent

        OrderShippedEvent orderShippedEvent = OrderShippedEvent.builder()
                .shipmentId(shipOrderCommand.getShipmentId())
                .orderId(shipOrderCommand.getOrderId())
                .shipmentStatus(ShipmentStatus.SHIPMENT_COMPLETED)
                .build();

        AggregateLifecycle.apply(orderShippedEvent);
    }

    @EventSourcingHandler
    public void on(OrderShippedEvent event){
        this.shipmentId = event.getShipmentId();
        this.orderId = event.getOrderId();
        this.shipmentStatus = event.getShipmentStatus();
    }
}
