package com.github.bruce_mig.commons.events;

import com.github.bruce_mig.commons.utils.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelledEvent {
    private String orderId;
    private OrderStatus orderStatus;
}
