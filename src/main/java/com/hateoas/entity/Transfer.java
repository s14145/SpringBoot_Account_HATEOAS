package com.hateoas.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "Transfer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Transfer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name  = "transfer_id")
    private Long transferId;

    @Column(name  = "to_account_number")
    private Long toAccountNumber;

    @Column(name  = "from_account_number")
    private Long fromAccountNumber;

    @Column(name  = "transfer_amount")
    private BigDecimal transferAmount;

    @Enumerated
    @Column(name  = "transfer_mode")
    private TransferMode transferMode;
}
