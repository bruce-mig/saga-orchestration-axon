package com.github.bruce_mig.commons.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationSentEvent {
    private String notificationId;
    private String orderId;
    private String userId;
}
