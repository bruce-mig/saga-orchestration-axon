package com.github.bruce_mig.order_service.command.api.saga;

import com.github.bruce_mig.commons.commands.CompleteOrderCommand;
import com.github.bruce_mig.commons.commands.ShipOrderCommand;
import com.github.bruce_mig.commons.commands.ValidatePaymentCommand;
import com.github.bruce_mig.commons.events.OrderCompletedEvent;
import com.github.bruce_mig.commons.events.OrderShippedEvent;
import com.github.bruce_mig.commons.events.PaymentProcessedEvent;
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

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public OrderProcessingSaga(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event){
        log.info("OrderCreatedEvent in Saga for OrderId: {}", event.getOrderId());

        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(event.getUserId());
        User user = null;

        try {
            user = queryGateway.query(
                    getUserPaymentDetailsQuery,
                    ResponseTypes.instanceOf(User.class)
            ).join();
        }catch (Exception e){
            log.error(e.getMessage());
            // Start the Compensating transaction
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
            ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                    .shipmentId(UUID.randomUUID().toString())
                    .orderId(event.getOrderId())
                    .build();

            commandGateway.send(shipOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the compensating transaction
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

}
