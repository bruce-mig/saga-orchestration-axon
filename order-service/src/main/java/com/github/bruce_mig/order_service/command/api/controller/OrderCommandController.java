package com.github.bruce_mig.order_service.command.api.controller;

import com.github.bruce_mig.order_service.command.api.command.CreateOrderCommand;
import com.github.bruce_mig.order_service.command.api.model.OrderRestModel;
import com.github.bruce_mig.commons.utils.OrderStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderCommandController {

    @Autowired
    private transient CommandGateway commandGateway;


    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRestModel orderRestModel){
        String orderId = UUID.randomUUID().toString();
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(orderId)
                .addressId(orderRestModel.getAddressId())
                .productId(orderRestModel.getProductId())
                .quantity(orderRestModel.getQuantity())
                .orderStatus(OrderStatus.ORDER_CREATED)
                .build();

        commandGateway.sendAndWait(createOrderCommand);
        return ResponseEntity.ok("Order Created");
    }
}
