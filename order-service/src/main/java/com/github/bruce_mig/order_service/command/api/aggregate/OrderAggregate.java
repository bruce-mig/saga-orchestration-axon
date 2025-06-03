package com.github.bruce_mig.order_service.command.api.aggregate;

import com.github.bruce_mig.commons.commands.CancelOrderCommand;
import com.github.bruce_mig.commons.commands.CompleteOrderCommand;
import com.github.bruce_mig.commons.events.OrderCancelledEvent;
import com.github.bruce_mig.commons.events.OrderCompletedEvent;
import com.github.bruce_mig.order_service.command.api.command.CreateOrderCommand;
import com.github.bruce_mig.order_service.command.api.events.OrderCreatedEvent;
import com.github.bruce_mig.commons.utils.OrderStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private OrderStatus orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        // todo: Validate the Command
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public  void on(OrderCreatedEvent event){
        this.orderId = event.getOrderId();
        this.productId = event.getProductId();
        this.userId = event.getUserId();
        this.addressId = event.getAddressId();
        this.quantity = event.getQuantity();
        this.orderStatus = event.getOrderStatus();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand){
        // validate command
        // publish order completed event
        OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent.builder()
                .orderStatus(completeOrderCommand.getOrderStatus())
                .orderId(completeOrderCommand.getOrderId())
                .build();

        AggregateLifecycle.apply(orderCompletedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event){
        this.orderStatus = event.getOrderStatus();
    }

    @CommandHandler
    public void handle(CancelOrderCommand cancelOrderCommand){
        OrderCancelledEvent orderCancelledEvent = new OrderCancelledEvent();
        BeanUtils.copyProperties(cancelOrderCommand, orderCancelledEvent);

        AggregateLifecycle.apply(orderCancelledEvent);
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event){
        this.orderStatus = event.getOrderStatus();
    }
}
