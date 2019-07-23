package com.corekcioglu.elinvar.commons.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Account {
    @Id
    private UUID accountId;
    private String name;
    private String email;
    private Double amount;
}
