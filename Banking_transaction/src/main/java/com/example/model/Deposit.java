package com.example.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="deposits")
public class Deposit  extends ModelGeneral{
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transaction_amount", precision = 12, scale = 1, nullable = false)
    private BigDecimal transactionAmount;
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_id", referencedColumnName = "id",nullable = false)
    private Customer customer;
    public Deposit() {}

    public Deposit(Long id, BigDecimal transactionAmount, Customer customer) {
        this.id = id;
        this.transactionAmount = transactionAmount;
        this.customer = customer;
    }

    public Long getIdDeposit() {
        return id;
    }

    public void setIdDeposit(Long idDeposit) {
        this.id = idDeposit;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
