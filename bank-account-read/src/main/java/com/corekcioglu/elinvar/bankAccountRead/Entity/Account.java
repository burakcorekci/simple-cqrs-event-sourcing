package com.corekcioglu.elinvar.bankAccountRead.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "account")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Account extends AbstractEntity<UUID> {
    @Id
    @Column( columnDefinition = "BINARY(16)", length = 16 )
    private UUID id;
    private String name;
    @Column(unique=true)
    private String email;
    private Double amount;
}
