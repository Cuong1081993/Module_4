package com.example.service.Customer;

import com.example.model.Customer;
import com.example.model.Deposit;
import com.example.model.Withdraw;
import com.example.repository.CustomerRepository;
import com.example.repository.DepositRepository;
import com.example.repository.WithdrawRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final DepositRepository depositRepository;
    private final WithdrawRepository withdrawRepository;

    public CustomerService(CustomerRepository customerRepository,
                           DepositRepository depositRepository,
                           WithdrawRepository withdrawRepository) {
        this.customerRepository = customerRepository;
        this.depositRepository = depositRepository;
        this.withdrawRepository = withdrawRepository;
    }

    @Override
    public List<Customer> findAllByIdNot(long id) {
        return customerRepository.findAllByIdNot(id);
    }

    @Override
    public List<Customer> findAllByDeletedIsFalse() {
        return customerRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer getById(Long id) {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleted(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public void deletedById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public boolean existByIdEqual(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public void deposit(Customer customer, Deposit deposit) {
        Long customerId = customer.getId();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
        deposit.setCustomer(customer);
        depositRepository.save(deposit);
        customerRepository.incrementBalance(customerId,transactionAmount);

    }

    @Override
    public void withdraw(Customer customer, Withdraw withdraw) {
        Long customerId = customer.getId();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        withdraw.setCustomer(customer);
        withdrawRepository.save(withdraw);
        customerRepository.decrementBalance(customerId,transactionAmount);
    }
}
