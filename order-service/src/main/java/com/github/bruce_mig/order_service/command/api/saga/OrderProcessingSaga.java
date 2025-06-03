package com.github.bruce_mig.order_service.command.api.saga;

import com.github.bruce_mig.commons.commands.*;
import com.github.bruce_mig.commons.events.*;
import com.github.bruce_mig.commons.model.User;
import com.github.bruce_mig.commons.queries.GetUserPaymentDetailsQuery;
import com.github.bruce_mig.commons.utils.OrderStatus;
import com.github.bruce_mig.order_service.command.api.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;


    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event){
        log.info("OrderCreatedEvent in Saga for OrderId: {}", event.getOrderId());

        String userId = event.getUserId();

        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(userId);
        User user = null;

        try {
            user = queryGateway.query(
                    getUserPaymentDetailsQuery,
                    ResponseTypes.instanceOf(User.class)
            ).join();
        }catch (Exception e){
            log.error(e.getMessage());
            // Start the Compensating transaction
            cancelOrderCommand(event.getOrderId());
        }

        ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                .cardDetails(user.getCardDetails())
                .orderId(event.getOrderId())
                .paymentId(UUID.randomUUID().toString())
                .build();

        commandGateway.sendAndWait(validatePaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event){
        log.info("PaymentProcessedEvent in Saga for OrderId: {}", event.getOrderId());

        try {

            /*if(true){
                throw new Exception();
            }*/

            ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                    .shipmentId(UUID.randomUUID().toString())
                    .orderId(event.getOrderId())
                    .build();

            commandGateway.send(shipOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the compensating transaction
            cancelPaymentCommand(event);
        }
    }


    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent event){
        log.info("OrderShippedEvent in Saga for OrderId: {}", event.getOrderId());

        CompleteOrderCommand completeOrderCommand  =CompleteOrderCommand.builder()
                .orderId(event.getOrderId())
                .orderStatus(OrderStatus.ORDER_APPROVED)
                .build();

        commandGateway.send(completeOrderCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCompletedEvent event){
        log.info("OrderCompletedEvent in Saga for OrderId: {}", event.getOrderId());

        // todo : add notification service
    }

    private void cancelOrderCommand(String orderId) {
        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(orderId);
        commandGateway.send(cancelOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCancelledEvent event){
        log.info("OrderCancelledEvent in Saga for OrderId: {}", event.getOrderId());
        // todo: send alert cancellation command
    }

    private void cancelPaymentCommand(PaymentProcessedEvent event) {
        CancelPaymentCommand cancelPaymentCommand = new CancelPaymentCommand(event.getPaymentId(), event.getOrderId());
        commandGateway.send(cancelPaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentCancelledEvent event){
        log.info("PaymentCancelledEvent in Saga for OrderId: {}", event.getOrderId());

        cancelOrderCommand(event.getOrderId());
    }

}
