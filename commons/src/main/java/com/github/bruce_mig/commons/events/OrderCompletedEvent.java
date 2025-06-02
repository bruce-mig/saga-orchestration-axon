package com.github.bruce_mig.commons.events;

import com.github.bruce_mig.commons.utils.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCompletedEvent {
    private String orderId;
    private OrderStatus orderStatus;
}
