package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transfer")
public class Transfer extends ModelGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fees;

    @Column(name = "fee_amount", precision = 12, scale = 0, nullable = false)
    private BigDecimal feeAmount;
    @Column(name = "transaction_amount", precision = 12, scale = 0, nullable = false)
    private BigDecimal transactionAmount;
    @Column(name = "transfer_amount", precision = 12, scale = 0, nullable = false)
    private BigDecimal transferAmount;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private Customer sender;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    private Customer recipient;


}
