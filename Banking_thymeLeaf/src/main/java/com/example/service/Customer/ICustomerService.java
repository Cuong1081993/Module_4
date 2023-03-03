package com.example.service.Customer;

import com.example.model.Customer;
import com.example.model.Deposit;
import com.example.model.Transfer;
import com.example.model.Withdraw;
import com.example.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {
    List<Customer> findAllByIdNot(long id);

    List<Customer> findAllByDeletedIsFalse();

    List<Customer> findAllByIdNotAndDeletedIsFalse(Long id);

    void deposit(Customer customer, Deposit deposit);

    void withdraw(Customer customer, Withdraw withdraw);

    void transfer(Transfer transfer);
}
