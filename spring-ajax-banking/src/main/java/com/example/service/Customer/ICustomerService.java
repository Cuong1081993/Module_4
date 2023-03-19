package com.example.service.Customer;

import com.example.model.Customer;
import com.example.model.DTO.CustomerDTO;
import com.example.model.Deposit;
import com.example.model.Transfer;
import com.example.model.Withdraw;
import com.example.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {

    List<Customer> findAllByIdNot(long id);

    List<Customer> findAllByDeletedIsFalse();

    List<Customer> findAllByIdNotAndDeletedIsFalse(Long id);
    List<CustomerDTO> findAllByDeletedIsFalseDTO();

    List<CustomerDTO> findAllCustomerDTO();

    Deposit deposit(Deposit deposit);
    void incrementBalance(@Param("transactionAmount")BigDecimal transactionAmount, @Param("customer") Customer customer);

    void withdraw(Customer customer, Withdraw withdraw);

    void transfer(Transfer transfer);

    Boolean existsByEmail(String email);
}
