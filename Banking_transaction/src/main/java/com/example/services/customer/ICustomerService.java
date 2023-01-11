package com.example.services.customer;

import com.example.model.Customer;
import com.example.model.Deposit;
import com.example.model.Transfer;
import com.example.model.Withdraw;
import com.example.services.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {
    List<Customer> findAllByIdNot(Long id);

    List<Customer> findAllByDeletedIsFalse();

    void deposit(Customer customer, Deposit deposit);

    void withdraw(Customer customer, Withdraw withdraw);

    void transfer(Transfer transfer);
}
