package com.example.service.customer;

import com.example.model.Customer;
import com.example.model.Deposit;
import com.example.model.Transfer;
import com.example.model.Withdraw;
import com.example.model.dto.CustomerDTO;
import com.example.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {
    List<Customer> findAllByIdNot(long id);

    List<Customer> findAllByDeletedIsFalse();

    List<Customer> findAllByIdNotAndDeletedIsFalse(Long id);
    List<CustomerDTO> findAllByDeletedIsFalseDTO();

    List<Customer> findAllByDeletedIsFalseAndIdNot(Long senderId);

    List<CustomerDTO> findAllCustomerDTO();

    void deposit(Customer customer, Deposit deposit);

    void withdraw(Customer customer, Withdraw withdraw);

    void transfer(Transfer transfer);

    CustomerDTO update(CustomerDTO customerDTO, Long customerId);

    Boolean existsByEmail(String email);
}
