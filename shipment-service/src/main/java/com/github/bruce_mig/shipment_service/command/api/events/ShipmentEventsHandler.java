package com.github.bruce_mig.shipment_service.command.api.events;

import com.github.bruce_mig.commons.events.OrderShippedEvent;
import com.github.bruce_mig.shipment_service.command.api.data.Shipment;
import com.github.bruce_mig.shipment_service.command.api.data.ShipmentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ShipmentEventsHandler {

    private final ShipmentRepository shipmentRepository;

    public ShipmentEventsHandler(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @EventHandler
    public void on(OrderShippedEvent event){
        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(event, shipment);
        shipmentRepository.save(shipment);
    }
}
