package com.example.services.customer;

import com.example.model.Customer;
import com.example.model.Deposit;
import com.example.model.Transfer;
import com.example.model.Withdraw;
import com.example.repository.CustomerRepository;
import com.example.repository.DepositRepository;
import com.example.repository.TransferRepository;
import com.example.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private WithdrawRepository withdrawRepository;

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
    public void delete(Customer customer) {
        customerRepository.delete(customer);

    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);

    }

    @Override
    public Boolean existsByIdEquals(long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public List<Customer> findAllByIdNot(Long id) {
        return customerRepository.findAllByIdNot(id);
    }

    @Override
    public List<Customer> findAllByDeletedIsFalse() {
        return customerRepository.findAllByDeletedIsFalse();
    }

    @Override
    public void deposit(Customer customer, Deposit deposit) {
        Long customerId = customer.getId();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
        deposit.setCustomer(customer);
        depositRepository.save(deposit);
        customerRepository.incrementBalance(customerId, transactionAmount);

    }

    @Override
    public void withdraw(Customer customer, Withdraw withdraw) {
        Long customerId = customer.getId();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        withdraw.setCustomer(customer);
        withdrawRepository.save(withdraw);
        customerRepository.decrementBalance(customerId, transactionAmount);
    }

    @Override
    public void transfer(Transfer transfer) {
        Long senderId = transfer.getSender().getId();
        BigDecimal transactionAmount = transfer.getTransactionAmount();
        customerRepository.decrementBalance(senderId, transactionAmount);
        Long recipientId = transfer.getRecipient().getId();
        BigDecimal transferAmount = transfer.getTransferAmount();

        customerRepository.incrementBalance(recipientId, transferAmount);
        transferRepository.save(transfer);
    }
}
