package com.hateoas.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "Account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Account implements Serializable {

    @Id
    @Column(name  = "account_number", length = 9)
    private Long accountNumber;

    @Column(name  = "amount")
    private BigDecimal amount;

    @Column(name  = "rate_of_interest")
    private double rateOfInterest;

    @Enumerated
    @Column(name  = "account_type")
    private AccountType accountType;

    @Enumerated
    @Column(name  = "account_status")
    private AccountStatus accountStatus;
}