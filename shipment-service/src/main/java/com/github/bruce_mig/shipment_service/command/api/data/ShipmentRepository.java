package com.github.bruce_mig.shipment_service.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, String> {
}
