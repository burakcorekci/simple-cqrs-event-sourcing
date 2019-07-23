package com.corekcioglu.elinvar.commons.Entity.Event;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class AbstractEvent {
    private UUID accountId;
    private String eventType;

    public AbstractEvent() {
        this.setEventType(getClass().getSimpleName());
    }
}
