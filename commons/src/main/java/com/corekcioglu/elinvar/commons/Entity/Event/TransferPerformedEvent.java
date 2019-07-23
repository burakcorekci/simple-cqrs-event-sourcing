package com.corekcioglu.elinvar.commons.Entity.Event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferPerformedEvent extends AbstractEvent {
    private UUID targetId;
    private Double amount;
}
