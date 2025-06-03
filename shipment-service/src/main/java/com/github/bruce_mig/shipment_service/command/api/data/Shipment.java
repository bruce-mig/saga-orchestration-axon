package com.github.bruce_mig.shipment_service.command.api.data;

import com.github.bruce_mig.commons.utils.ShipmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {
    @Id
    private String shipmentId;
    private String orderId;
    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus;
}
