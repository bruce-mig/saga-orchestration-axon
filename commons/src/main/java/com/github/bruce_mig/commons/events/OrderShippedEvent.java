package com.github.bruce_mig.commons.events;

import com.github.bruce_mig.commons.utils.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderShippedEvent {
    private String shipmentId;
    private String orderId;
    private ShipmentStatus shipmentStatus;
}
