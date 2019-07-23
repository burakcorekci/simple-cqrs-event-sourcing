package com.corekcioglu.elinvar.commons.Entity.Event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WithdrawalPerformedEvent extends AbstractEvent {
    private Double amount;
}
