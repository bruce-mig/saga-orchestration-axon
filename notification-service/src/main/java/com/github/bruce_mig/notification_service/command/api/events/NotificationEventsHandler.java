package com.github.bruce_mig.notification_service.command.api.events;

import com.github.bruce_mig.commons.events.NotificationSentEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationEventsHandler {

    @EventHandler
    public void on(NotificationSentEvent event){
        log.info("Sent notification to user with id: {}", event.getUserId());
    }
}
